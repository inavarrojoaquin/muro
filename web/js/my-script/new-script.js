$(document).ready(function(){
    var id_user = $("input[name=id_user]").val();
    var userName = $("input[name=userName]").val();
    var selectionCareer = "";
    var selectionSubject = "";
    var id_careerSelected;
    var id_subjectSelected;
    var id_wall = -1;
    var endDatePublication = "-1";

//trae el primer input hidden de las publicaciones, en este caso seria la primer publicacion q se muestra
//$("div#publications .publication:first input:hidden:first").val();

    function getLast5Publications() {
        if(id_wall !== -1 && endDatePublication !== "-1"){
            var promiseTopPublications = callAJAX('publication.do', {id_wall:id_wall, endDatePublication:endDatePublication});
            promiseTopPublications
                    .then(function(vector){
                        if(!vector.error){
                            loadPublications(vector);
                        }else {
                            setError(vector.error);
                        }
                    });
        }        
    }   
    //Reload muro's publications every 5 minutes
//    setInterval(getLast5Publications, 300000); // 1min = 60000 , 3min = 180000 
    generateCareerList();
    
    /** If the user clicks in 'career' label show the career list*/
    $("#idCarrera").on("click", function(){
        $("#carreras ul").show();
    });

    /** Generate subjects list*/
    $("#carreras").on("click", "li", function(){
       cleanError();
       hideInsertPublication();
       cleanPublications();
       var careerText = $(this).text();
       if (selectionCareer !== careerText){
            selectionCareer = careerText;
            id_careerSelected = $("input[name='"+selectionCareer+"']").val();
            generateSubjectList(id_careerSelected);
       }else {
            setError("La carrera ya fue seleccionada");
       }
    });
    
    /** Obtain id_muro related with subject selected and obtain muro's publications*/
   $("#materias").on("click", "li", function(){
       cleanError();
       cleanPublications();
       subjectText = $(this).text();
       if(selectionSubject !== subjectText){
           selectionSubject = subjectText;
           id_subjectSelected = $("input[name='"+selectionSubject+"']").val();
           publicationProcess(id_careerSelected, id_subjectSelected, id_user);
       }else {
            setError("Selecciono la misma materia");
       } 
   });
   
   $("#insertContent").on('click', function(){
       var maxLength = 150;
       var content = $.trim($("#contentPublication").val());
       if(content.length === 0 || content.length > maxLength){
           setError("Contenido vacio o mayor a 150 caracteres");
       }else {
            insertPublication(content, id_wall, id_user);
       }
   });
    /**Insert publication in the actual muro*/
    function insertPublication(content,id_wall, id_user){
        var promiseInsert = callAJAX('publication.do', {text:content, id_wall: id_wall, id_user: id_user}, 'POST');
        promiseInsert.
                then(function(data){
                    if(!data.error){
                        cleanContentPublication();
                        setSuccess(data.mensaje);
                        getLast5Publications();
                    }else {
                        setError(data.error);
                    }
                });
    }
    
    /**when the user clicked in 'Ver comentarios'
     * The first time call ajax. Then show and hide comments.
     * */
    $("#publications").on('click','article span', function(){
        var action = $(this).text();
        var comments = $(this).siblings('#comments').children();
        
        if(action == "Ver comentarios" & comments.length == 0 ){
            $(this).text("Ocultar comentarios");
            var id_publication = $(this).siblings("input:hidden[name=publication]").val();
            commentsListByPublication(id_publication);
        }else if(action == "Ver comentarios" & comments.length > 0){
            $(this).text("Ocultar comentarios");
            $(this).siblings("div#comments").show();
        }else{
            $(this).text("Ver comentarios");
            $(this).siblings("div#comments").hide();
        }
            
    });
    
    /**when the user want insert a comment in one particular publication*/
    $("#publications").on('click','input[name=submitComment]', function(){
        var id_publication = $(this).siblings('input:hidden[name=publication]').val();
        var input = $(this).siblings('input:text[name=textComment]');
        var inputText = $.trim(input.val());
        var maxLength = 150;
        var span = $(this).siblings('span');
       if(inputText.length === 0 || inputText.length > maxLength){
           setError("Contenido vacio o mayor a 150 caracteres");
       }else {
            var promiseComment = callAJAX('comment.do', {text:inputText, id_user:id_user, id_publication:id_publication}, 'POST');
            promiseComment
                    .then(function(vector){
                            if(!vector.error){
                                input.val('');
                                //setSuccess(vector.mensaje);
                                span.click(); //simulate click on span. preview defined
                            }else {
                                setError(vector.error);
                            }
                        });
       }   
    });
    
    /**Update like in publiction when the user click in the button called 'Me gusta'*/
   $('#publications').on('click', 'input[name=like]', function() { 
        var id_publicationToInsert = $(this).siblings('input:hidden[name=publication]').val();
        var thisElement = $(this).siblings('label[name=labelLike]').children('span');
        var like = $(this).val('Esto te gusta');
        var disableButton = $(this).attr('disabled', true);
        var valueLike = parseInt(thisElement.text()) + 1;
        
        /**Insert like in the table and update like variable in the publication's table using trigger*/
        var promiseLike = callAJAX('publication.do', {method:'put', id_user:id_user, id_publication:id_publicationToInsert}, 'POST');
        promiseLike
                .then(function(vector){
                    if(!vector.error){
                        thisElement.text(valueLike.toString());
                        like;
                        disableButton;
                    }else {
                        setError(vector.error);
                    }
                });
   });
   
    /**The process get id_muro then get and show the muro's publications*/
    function publicationProcess(id_careerSelected, id_subjectSelected, id_user){
        var promiseWall = callAJAX('wall.do', {id_career:id_careerSelected, id_subject:id_subjectSelected});
        var promisePublication = promiseWall
                                    .then(function(vector){
                                        if(!vector.error){
                                            id_wall = vector.id_muro;
                                            return callAJAX('publication.do', {id_wall:id_wall, id_user:id_user}); //AJAX return a promise
                                        }else {
                                            setError(vector.error);
                                        }
                                    });
        promisePublication
                .then(function(vector){
                    if(!vector.error){
                        showInsertPublication();
                        loadPublications(vector);
                    }else {
                        setError(vector.error);
                    }
                });
    }
    /**Load publications in muro*/
    function loadPublications(vector){
        for(var i = 0; i < vector.length; i++) {
            var id_publication = vector[i].id_publicacion;
            var publicationText = vector[i].texto;
            var publicationLikes = vector[i].likes;
            var userLike = vector[i].userLike;
            var publicationIdUser = vector[i].id_usuario;
            var publicationUserName = vector[i].nombreUsuario;
            var publicationDate = vector[i].fecha_publicacion;
            endDatePublication = publicationDate;

            $("#publications").prepend("<article id='"+id_publication+"' class='publication'></article>");
            $("#publications article:first").append("<input type='hidden' name='publication' value='"+id_publication+"'></input>");
            $("#publications article:first").append("<label>Publicado por: "+publicationUserName+"</label>");
            $("#publications article:first").append("<label> Fecha publicacion: "+publicationDate+"</label></br>");
            detectContent("#publications article:first", publicationText);
            if(userLike == "Esto te gusta"){
                $("#publications article:first").append("<input class='likePublication' type='button' name='like' value='"+userLike+"' disabled></input>");
            }else{
                $("#publications article:first").append("<input class='likePublication' type='button' name='like' value='"+userLike+"'></input>");
            }
            $("#publications article:first").append("<input type='button' name='share' value='Compartir'><a href='https://www.facebook.com/sharer/sharer.php?u=http://localhost:8080/muro/muro.jsp#"+id_publication+"' target='_blank'>\n\
                                                <img src='https://lh3.googleusercontent.com/-H8xMuAxM-bE/UefWwJr2vwI/AAAAAAAAEdY/N5I41q19KMk/s32-no/facebook.png'></a></input>");
                    
            $("#publications article:first").append("<label name='labelLike'> Likes: <span>"+publicationLikes+"</span></label>");
            $("#publications article:first").append("<input type='text' name='textComment' placeholder='Ingrese comentario'></input>");
            $("#publications article:first").append("<input type='button' name='submitComment' value='Comentar'></input>");
            $("#publications article:first").append("</br><span>Ver comentarios</span>");
            $("#publications article:first").append("<div id='comments'></div>");
        }
    }
    
    function commentsListByPublication(id_publication){
        var promiseCommentsByPublication = callAJAX('comment.do', {id_publication:id_publication});
            promiseCommentsByPublication
                        .then(function(vector){
                            if(!vector.error){
                                loadCommentsInPublication(vector);
                            }
                        });
    }
    
    function loadCommentsInPublication(vector){
        for(var i = 0; i < vector.length; i++){
            var id_comment = vector[i].id_comentario;
            var text = vector[i].texto;
            var id_publication = vector[i].id_publicacion;
            var userName = vector[i].nombre+" "+vector[i].apellido;
            var commentDate = vector[i].fecha_creacion;
            $("article#"+id_publication).children("#comments").prepend("<div id='"+id_comment+"' class='comment'></div>");
            $("article#"+id_publication).children("#comments").find("div:first").append("<p>"+text+"</p>");
            $("article#"+id_publication).children("#comments").find("div:first").append("<label>Comentado por: "+userName+"</label>");
            $("article#"+id_publication).children("#comments").find("div:first").append("<label> Fecha: "+commentDate+"</label>");
        }
    }
    
});

/**Generate career list when the page load*/
function generateCareerList(){
    var promiseCareer = callAJAX('career.do');
    promiseCareer
            .then(function(vector){
                if(!vector.error){
                    $("#carreras").append("<ul></ul>");
                    $("#carreras ul").hide();
                    for(var i = 0; i < vector.length; i++) {
                        var id_career = vector[i].id_carrera;
                        var name = vector[i].nombre;
                        $("#carreras ul").append("<li><input type='hidden' name='"+name+"' value='"+id_career+"' />" +name+ "</li>");
                }
                }else {
                    setError(vector.error);
                }
    });
}
/**Generate subjects list when the user clicks on a career*/
function generateSubjectList(id_careerSelected){
    var promiseSubject = callAJAX('subject.do', {id_career:id_careerSelected});
    promiseSubject.done(function(vector){
        if(!vector.error){
            if($("#materias ul").length === 0){
                $("#materias").append("<ul></ul>");
            }
            $("#materias ul").empty();
            for(var i = 0; i < vector.length; i++) {
                var id_subject = vector[i].id_materia;
                var name = vector[i].nombre;    
                $("#materias ul").append("<li><input type='hidden' name='"+name+"' value='"+id_subject+"' />" +name+ "</li>");
            }
        }else{
            setError(vector.error);
        }
    });
};

/**Method responsible to get values*/
function callAJAX(url, parameters, type){
     return $.ajax({
            url: url,
            data: parameters,
            type: type,
            datType: 'json',
            error: callback_error
        });     
}

function detectContent(tag, text){
    var linkRegex = /^(ht|f)tp(s?):\/\/\w+([\.\-\w]+)?\.([a-z]{2,4}|travel)(:\d{2,5})?(\/.*)?$/i;
    var imageRegex = /\.(jpeg|jpg|gif|png)$/;
    var videoRegex = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/; 
    var urlToValidate = text;
    
    if (linkRegex.test(urlToValidate)){
        var array = urlToValidate.split('\\');
        if(imageRegex.test(urlToValidate)){
            for(var i = 0; i < array.length; i++){
                $(tag).append("<p><img src='"+array[i]+"' alt='Imagen X' height='42' width='42'></p>");
            }
        }else if(videoRegex.test(urlToValidate)){
            for(var i = 0; i < array.length; i++){
                var match = array[i].match(videoRegex); 
                if (match && match[2].length == 11) { 
                    $(tag).append("<iframe src='https://www.youtube.com/embed/"+match[2]+"' width='250' height='250' frameborder='0'></iframe>");
                    $(tag).append("<p><a href='"+array[i]+"'>Ver video en Youtube</a></p>");
                }
            }        
        }else{
            $(tag).append("<p><a href='"+urlToValidate+"' target='_blank'>Link a: '"+urlToValidate+"'</a></p>");
        }
    }else {
            $(tag).append("<p>"+urlToValidate+"</p>");
    }
}

function hideInsertPublication(){
    $("#insertPublication").hide();
}

function showInsertPublication(){
    $("#insertPublication").show();
}

function cleanError(){
    $(".error").empty();
}

function cleanPublications(){
    $("#publications").empty();
}

function cleanContentPublication(){
    $("#contentPublication").val('');
}

function setSuccess(success){
    setTimeout(function() {
        $(".success").append("<span>"+success+"</span>").fadeOut(5000);
    },0);
}

function setError(error){
    setTimeout(function() {
        $(".error").append("<span>"+error+"</span>").fadeOut(10000);
    },0);
}

function callback_error(XMLHttpRequest, textStatus, errorThrown){
    setError("<span>Error Servlet: "+ XMLHttpRequest.status +" ,"+ textStatus +" ,"+ errorThrown +"</span>");
}