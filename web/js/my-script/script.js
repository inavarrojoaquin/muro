$(document).ready(function(){
    var id_user = $("input[name=id_user]").val();
    var userName = $("input[name=userName]").val();
    var selectionCareer = "";
    var selectionSubject = "";
    var id_careerSelected;
    var id_subjectSelected;
    var id_wall = "";
    
    hideInsertPublication();
    generateCareerList();
    
    /** If the user clicks in 'career' label show the career list*/
    $("#idCarrera").on("click", function(){
        $("#carreras ul").show();
    });
    
    /** Generate subjects list*/
    $("#carreras").on("click", "li", function(){
       cleanError();
       cleanPublications();
       hideInsertPublication();
       if (selectionCareer !== $(this).text()){
        selectionCareer = $(this).text();
        id_careerSelected = $("input[name='"+selectionCareer+"']").val();
        if($("#materias ul").length === 0){
            $("#materias").append("<ul></ul>");
        }
        getValues('subject.do', 'id_career='+id_careerSelected, function(vector){
            $("#materias ul").empty();
            for(var i = 0; i < vector.length; i++) {
                var id_subject = vector[i].id_materia;
                var name = vector[i].nombre;    
                $("#materias ul").append("<li><input type='hidden' name='"+name+"' value='"+id_subject+"' />" +name+ "</li>");
            }
        });
      }else {
            setError("La carrera ya fue seleccionada");
      }
   });
   
   /** Obtain id_muro related with subject selected and obtain muro's publications*/
   $("#materias").on("click", "li", function(){
       cleanError();
       hideInsertPublication();
       if(selectionSubject !== $(this).text()){
           selectionSubject = $(this).text();
           id_subjectSelected = $("input[name='"+selectionSubject+"']").val();
            getValues("wall.do", "id_career="+id_careerSelected+"&id_subject="+id_subjectSelected, function(vector){
                id_wall = vector.id_muro;
                if(id_wall !== ""){
                    getValuesPublication(id_wall, id_user);
                }
            });
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
            var parameters = {
                "text"   : content,
                "id_wall" : id_wall,
                "id_user" : id_user
            };
            interactWhithPublication('publication.do', parameters, 'POST', function(data){
                setSuccess(data.mensaje);
                getValuesPublication(id_wall, id_user);
            });
       }
   });
   
   /**Update like in publiction when the user click in the button called 'Me gusta'*/
   $('#publications').on('click', 'input[name=like]', function() { 
        var publicationToInsert = $(this).siblings('input:hidden[name=publication]').val();
        var thisElement = $(this).siblings('label[name=labelLike]').children('span');
        var like = $(this).val('Esto te gusta');
        var disableButton = $(this).attr('disabled', true);
        var valueLike = parseInt(thisElement.text()) + 1;

        interactWithLikePublication({'id_user':id_user, 'id_publication':publicationToInsert}, function(data){
            thisElement.text(valueLike.toString());
            like;
            disableButton;
        });
   });
});

function hideInsertPublication(){
    $("#insertPublication").hide();
}

function showInsertPublication(){
    $("#insertPublication").show();
}
/** Generate careers list*/
function generateCareerList(){
    $("#carreras").append("<ul></ul>");
    getValues('career.do', '', function(vector){    
        $("#carreras ul").hide();
        for(var i = 0; i < vector.length; i++) {
            var id_career = vector[i].id_carrera;
            var name = vector[i].nombre;
            $("#carreras ul").append("<li><input type='hidden' name='"+name+"' value='"+id_career+"' />" +name+ "</li>");
        }
    });
}

/**Interact with update and select in the publication*/
function interactWithLikePublication(param, my_callback){
    var parameters = {
        "method": "put",
        "id_user" : param.id_user,
        "id_publication": param.id_publication
    };
    interactWhithPublication('publication.do', parameters, 'POST', function(data){
        my_callback(data);
    });
}

function getValuesPublication(id_wall, id_user){
    cleanPublications();
    cleanContentPublication();
    getValues("publication.do", "id_wall="+id_wall+"&id_user="+id_user, function(vector) {
        showInsertPublication();
        for(var i = 0; i < vector.length; i++) {
            var id_publication = vector[i].id_publicacion;
            var publicationText = vector[i].texto;
            var publicationLikes = vector[i].likes;
            var userLike = vector[i].userLike;
            var publicationIdUser = vector[i].id_usuario;
            var publicationUserName = vector[i].nombreUsuario;
            var publicationDate = vector[i].fecha_publicacion;

            $("#publications").append("<article id='publication"+i+"' class='publication'></article>");
            $("#publication"+i).append("<input type='hidden' name='publication' value='"+id_publication+"'></input>");
            $("#publication"+i).append("<label>Publicado por: "+publicationUserName+"</label>");
            $("#publication"+i).append("<label> Fecha publicacion: "+publicationDate+"</label></br>");
            detectContent("#publication"+i, publicationText);
            if(userLike == "Esto te gusta"){
                $("#publication"+i).append("<input class='likePublication' type='button' name='like' value='"+userLike+"' disabled></input>");
            }else{
                $("#publication"+i).append("<input class='likePublication' type='button' name='like' value='"+userLike+"'></input>");
            }
            $("#publication"+i).append("<input type='button' name='share' value='Compartir'></input>");
            $("#publication"+i).append("<label name='labelLike'> Likes: <span>"+publicationLikes+"</span></label>");
            $("#publication"+i).append("<input type='text' name='textComment' value='Ingrese comentario'></input>");
            $("#publication"+i).append("<input type='button' name='submitComment' value='Comentar'></input>");
        }
   });
//   interactWithLikePublication({'id_user':id_user}, function(data){
//        for(var i = 0; i < data.length; i++){
//            var publication = $("#publications input[value='"+data[i].id_publicacion+"']");
//            $(publication).siblings("input[name=like]").val("Te gusta");
//        }
//   });
}

/**Como Ajax es asincrono y necesito retornar el vector, uso la funcion my_callback donde le paso el vector como parametro.
 * No es recomedable usar la propiedad async: false
 * De esta forma, al devolver los datos, podemos trabajarlos en otra funcion y desaclopar el codigo
 * */
function getValues(url, parameter, my_callback){
    $.ajax({
        url: url,
        data: parameter,
        method: 'GET',
        datType: 'json',
        beforeSend: function(){},
        success: function(data){
            if(!data.error){
                my_callback(data);
            }else{
                setError(data.error);
            }
        },
        error: callback_error,
        complete: function(){} //equivalente al always, siempre se ejecuta, sea success o fail
    });     
}

function interactWhithPublication(url, parameter, type, callback){
    $.ajax({
        url: url,
        data: parameter,
        type: type,
        datType: 'json',
        beforeSend: function(){},
        success: function(data){
            if(!data.error){
                callback(data);
            }else{
                setError(data.error);
            }
        },
        error: callback_error,
        complete: function(){} //equivalente al always, siempre se ejecuta, sea success o fail
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

function setSuccess(success){
    $(".success").append("<span>"+success+"</span>");
}
function setError(error){
    $(".error").append("<span>"+error+"</span>");
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

function callback_error(XMLHttpRequest, textStatus, errorThrown){
    setError("<span>Error Servlet: "+ XMLHttpRequest.status +" ,"+ textStatus +" ,"+ errorThrown +"</span>");
}

/** Ejemplos de acceso
 * <input type="hidden" id="foo" name="zyx" value="bar" />

    alert($('input#foo').val());
    alert($('input[name=zyx]').val());
    alert($('input[type=hidden]').val());
    alert($(':hidden#foo').val());
    alert($('input:hidden[name=zyx]').val());
 * */

//    $("#borrarPublicacion").on('click', function(){
//        var parameters = {
//            "id_publication" : "1004",
//            "method" : "delete"
//        };
//        interactWhithPublication('publication.do', parameters, 'POST', function(data){
//            alert(data.mensaje);
//        });
//    });
//    $("#deshabilitarPublicacion").on('click', function(){
//        var parameters = {
//            "method": "put",
//            "id_publication": "1003",
//            "enable": "false"
//        };
//        interactWhithPublication('publication.do', parameters, 'POST', function(data){
//            alert(data.mensaje);
//        });
//    });
//    $("#habilitarPublicacion").on('click', function(){
//        var parameters = {
//            "method": "put",
//            "id_publication": "1003",
//            "enable": "true"
//        };
//        interactWhithPublication('publication.do', parameters, 'POST', function(data){
//            alert(data.mensaje);
//        });
//    });


