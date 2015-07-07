$(document).ready(function(){
    var id_user = $("input[name=id_user]").val();
    var userName = $("input[name=userName]").val();
    var id_role = $("input[name=id_role]").val();
    var selectionCareer = "";
    var selectionSubject = "";
    var id_careerSelected;
    var id_subjectSelected;
    var id_wall = -1;
    var endDatePublication = "-1";

/**
 * (1,'DIRECTOR'),
 * (2,'PROFESOR'),
 * (3,'ALUMNO')
 * */

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
    setInterval(getLast5Publications, 300000); // 1min = 60000 , 3min = 180000, 5min = 300000 
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
    
    /**Clickes in some subject. Obtain id_muro related with subject selected and obtain muro's publications*/
   $("#materias").on("click", "li span", function(){
       var subjectText = $(this).text();
       id_wall = $(this).siblings("input:hidden[name=id_wall]").val();
       if(selectionSubject !== subjectText){
           cleanError();
           cleanPublications();
           selectionSubject = subjectText;
           //id_subjectSelected = $("input[name='"+selectionSubject+"']").val();
           publicationProcess(id_wall, id_user);
       }else {
            setError("Selecciono la misma materia");
       } 
   });
   
   /**When the user clicked in the subject's checkbox the wall disable or enable*/
   $("#materias").on('click', 'li input:checkbox', function(){
       var enableMuro = $(this).is(':checked');
       var subjectToModify = $(this).siblings('input:hidden').val();
       var promiseModifyMuro = callAJAX('subject.do', {id_career:id_careerSelected, id_subject:subjectToModify, enable:enableMuro}, 'POST');
       promiseModifyMuro
               .then(function(data){
                    if(!data.error){
                        setSuccess(data.mensaje);
                        selectionSubject = "";
                        hideInsertPublication();
                        cleanPublications();
                    }else {
                        setError(data.error);
                    }
                });
   });
   
   /**When the user clicks in optionsMenu because he wants to apply more options*/
   $("#publications").on('click','article .optionsMenu span', function(){
       var dropdownList = $(this).next();
       dropdownList.toggle('fast');
   });
   
   /**When the user select one option in the option's menu*/
   $("#publications").on('click','article .optionsMenu li', function(){
        var optionName = $(this).text();
        switch(optionName){
            case 'Eliminar': 
                  var publicationClass = $(this).parent().siblings('span').hasClass('publicationOptions');
                  var id_publication = $(this).parents('div').siblings('input:hidden[name=publication]').val();
                  if(publicationClass){
                        deletePublication(id_publication);
                  }else{
                      var id_comment = $(this).parents('div .comment').attr('id');
                      deleteComment(id_publication, id_comment);
                  }
                  break;
        }
    });
    
   /***/
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
    $("#publications").on('click','article .publicationComments', function(){
        var action = $(this).text();
        var comments = $(this).siblings('.comments').children();
        
        if(action == "Ver comentarios" & comments.length == 0 ){
            $(this).text("Ocultar comentarios");
            var id_publication = $(this).siblings("input:hidden[name=publication]").val();
            commentsListByPublication(id_publication);
        }else if(action == "Ver comentarios" & comments.length > 0){
            $(this).text("Ocultar comentarios");
            $(this).siblings("div.comments").show();
        }else{
            $(this).text("Ver comentarios");
            $(this).siblings("div.comments").hide();
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
    function publicationProcess(id_wall, id_user){
        var promisePublication = callAJAX('publication.do', {id_wall:id_wall, id_user:id_user}); //AJAX return a promise
        promisePublication
                .then(function(vector){
                    if(!vector.error){
                        showInsertPublication();
                        loadPublications(vector);
                        hideOptionsMenu();
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
            var isLike = (userLike == "Esto te gusta") ? "disabled": "";
            endDatePublication = publicationDate;
            var content = detectContent(publicationText);
            var isProfesor = (id_role == 2) ? " <div class='optionsMenu'><span class='publicationOptions'>Opciones</span><ul class='sub-menu'><li>Eliminar</li></ul></div>" : "";

            $("#publications").prepend("<article id='"+id_publication+"' class='publication'></article>");
            $("#publications article:first").append("<input type='hidden' name='publication' value='"+id_publication+"'></input>");
            $("#publications article:first").append("<label>Publicado por: "+publicationUserName+"</label>");
            $("#publications article:first").append("<label> Fecha publicacion: "+publicationDate+"</label>");
            $("#publications article:first").append(isProfesor);
            $("#publications article:first").append(content);
            $("#publications article:first").append("<input class='likePublication' type='button' name='like' value='"+userLike+"' "+isLike+"></input>");        
            $("#publications article:first").append("<label name='labelLike'> Likes: <span>"+publicationLikes+"</span></label>");
            $("#publications article:first").append("<input type='text' name='textComment' placeholder='Ingrese comentario'></input>");
            $("#publications article:first").append("<input type='button' name='submitComment' value='Comentar'></input>");
            $("#publications article:first").append("</br><span class='publicationComments'>Ver comentarios</span>");
            $("#publications article:first").append("<div class='comments'></div>");
        }
    }
    
    function commentsListByPublication(id_publication){
        var promiseCommentsByPublication = callAJAX('comment.do', {id_publication:id_publication});
            promiseCommentsByPublication
                        .then(function(vector){
                            if(!vector.error){
                                loadCommentsInPublication(vector);
                                hideOptionsMenu();
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
            
            var isProfesor = (id_role == 2) ? " <div class='optionsMenu'><span class='commentOptions'>Opciones</span><ul class='sub-menu'><li>Eliminar</li></ul></div>" : "";
            
            $("article#"+id_publication).children(".comments").prepend("<div id='"+id_comment+"' class='comment'></div>");
            $("article#"+id_publication).children(".comments").find("div:first").append("<p>"+text+"</p>");
            $("article#"+id_publication).children(".comments").find("div:first").append("<label>Comentado por: "+userName+"</label>");
            $("article#"+id_publication).children(".comments").find("div:first").append("<label> Fecha: "+commentDate+"</label>");
            $("article#"+id_publication).children(".comments").find("div:first").append(isProfesor);
        }
    }
    
    /**Generate subjects list when the user clicks on a career*/
    function generateSubjectList(id_careerSelected){
        var promiseSubject = callAJAX('subject.do', {id_career:id_careerSelected});
        promiseSubject
                .then(function(vector){
                    if(!vector.error){
                        loadSubjectList(vector);
                    }else{
                        setError(vector.error);
                    }
                });
    };
    
    function loadSubjectList(vector){
        if($("#materias ul").length === 0){
            $("#materias").append("<ul></ul>");
        }
        $("#materias ul").empty();
        for(var i = 0; i < vector.length; i++) {
            var id_subject = vector[i].id_materia;
            var name = vector[i].nombre;
            var wall = vector[i].muro;
            var id_wall = wall.id_muro;
            var enable = wall.habilitado;
            var checked = (enable) ? "checked" : "";
            
            var isProfesor = (id_role == 2) ? " <input type='checkbox' name='enableMuro' "+checked+" /> Habilitado" : "";
            $("#materias ul").append("<li></li>");
            $("#materias li:last").append("<input type='hidden' name='"+name+"' value='"+id_subject+"' /><span>" +name+ "</span>");
            $("#materias li:last").append(isProfesor);
            $("#materias li:last").append("<input type='hidden' name='id_wall' value='"+id_wall+"' />");
            
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

function deletePublication(id_publication){
    var promiseDeletePublication = callAJAX('publication.do', {id_publication:id_publication, method:'delete'}, 'POST');
    promiseDeletePublication
            .then(function(data){
                if(!data.error){
                    setSuccess(data.mensaje);
                    deleteDivPublication(id_publication);
                }else {
                    setError(data.error);
                }
            });
}
    
function deleteComment(id_publication, id_comment){
    var promiseDeleteComment = callAJAX('comment.do', {id_comment:id_comment, method:'delete'}, 'POST');
    promiseDeleteComment
            .then(function(data){
                if(!data.error){
                    setSuccess(data.mensaje);
                    deleteDivComment(id_publication, id_comment);
                }else {
                    setError(data.error);
                }
            });
}

/**Method responsible to call servlet and select, insert or update values*/
function callAJAX(url, parameters, type){
     return $.ajax({
            url: url,
            data: parameters,
            type: type,
            datType: 'json',
            error: callback_error
        });     
}

function detectContent(text) {
    var urlRegex = /(https?:\/\/[^\s]+)/g;
    var imageRegex = /\.(jpeg|jpg|gif|png)$/;
    var youtubeRegex = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var vimeoRegex = /vimeo\.com\/([0-9]{1,10})/;
    
    return text.replace(urlRegex, function(url) {
        if(url.match(imageRegex)){
            return "<p><img src='"+url+"' alt='Imagen X' height='42' width='42'></p>";
        }else if(url.match(vimeoRegex)){
            var match = url.match(vimeoRegex);
            if(match){
                id_vimeo = match[1];
                return "<iframe src='https://player.vimeo.com/video/"+id_vimeo+"' width='250' height='250' frameborder='0'></iframe>\n\
                        <p><a href='"+match+"' target='_blank'>Ver video en Vimeo</a></p>";
            }
        }else if(url.match(youtubeRegex)){
            var match = url.match(youtubeRegex);
            if(match){
                id_youtube = match[2];
                return "<iframe src='https://www.youtube.com/embed/"+id_youtube+"' width='250' height='250' frameborder='0'></iframe>\n\
                        <p><a href='"+match+"' target='_blank'>Ver video en Youtube</a></p>";
            }
        }else {
                return "<p><a href='"+url+"' target='_blank'>Link a: '"+url+"'</a></p>";
        } 
    });
}

function hideInsertPublication(){
    $("#insertPublication").hide();
}

function showInsertPublication(){
    $("#insertPublication").show();
}

function hideOptionsMenu(){
    $(".sub-menu").hide();
}

function deleteDivPublication(id_publication){
    $("#publications").find('article#'+id_publication).remove();
}

function deleteDivComment(id_publication, id_comment){
    $("#publications").find('article#'+id_publication+' .comments div#'+id_comment).remove();
}

function cleanError(){
    $(".error").empty();
}

function cleanSuccess(){
    $(".success").empty();
}

function cleanPublications(){
    $("#publications").empty();
}

function cleanContentPublication(){
    $("#contentPublication").val('');
}

function setSuccess(success){
    $(".success").append("<span>"+success+"</span>").slideToggle(5000, function(){cleanSuccess();});
}

function setError(error){
    $(".error").append("<span>"+error+"</span>").slideToggle(8000, function(){cleanError();});
}

function callback_error(XMLHttpRequest, textStatus, errorThrown){
    setError("<span>Error Servlet: "+ XMLHttpRequest.status +" ,"+ textStatus +" ,"+ errorThrown +"</span>");
}