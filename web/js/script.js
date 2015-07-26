$(document).ready(function(){
    var id_user = $("input[name=id_user]").val();
    var userName = $("input[name=userName]").val();
    var id_role = $("input[name=id_role]").val();
    var date = new Date();
    var lastAccessDateSession = sessionStorage.setItem("lastAccessDateSession", getFormattedDate(date));
    var selectionCareer = "";
    var selectionSubject = "";
    var id_careerSelected;
    var id_subjectSelected;
    var id_wall = -1;
    var endDatePublication = "";
    
    var id_careerSession = sessionStorage.getItem("id_career");
    var id_wallSession = sessionStorage.getItem("id_wall");
    var lastAccessDate = sessionStorage.getItem("lastAccessDateSession");

    /**Load facebook API*/
    $.getScript('templates/facebookAPI.js', function(){});
    /**Load twitter API*/
    $.getScript('templates/twitterAPI.js', function(){});
    
/**
 * (1,'DIRECTOR'),
 * (2,'PROFESOR'),
 * (3,'ALUMNO')
 * */

    //Reload muro's publications every 5 minutes
    setInterval(getLast5Publications, 15000); // 1min = 60000 , 3min = 180000, 5min = 300000 
    
    /**Generate careers list*/
    generateCareerList().then(function(){
        if(id_careerSession != null){
            $("#career-list").find("a[data-id-career='"+id_careerSession+"']").click ();           
        }        
    });
    
    /** Generate subjects list*/
    $("#career-list").on("click", "a", function(){
       var careerName = $(this).text();
        
       if (selectionCareer !== careerName){
            hideInsertPublication();
            cleanPublications();
            selectionCareer = careerName;
            id_careerSelected = $(this).attr('data-id-career');
            sessionStorage.setItem("id_career", id_careerSelected);
            generateSubjectList(id_careerSelected).then(function(){
                if(id_wallSession != null){
                        $("#subjects").find("a[data-id-wall='"+id_wallSession+"']").click ();
                    }
            });
       }else {
            showAlert("La carrera ya fue seleccionada", "alert-danger");
       }
    });
    
    /**Clickes in some subject. Obtain id_muro related with subject selected and obtain muro's publications*/
   $("#subjects").on("click", "a", function(){
       var subjectText = $(this).text();
       var enableWall = $(this).siblings().find("[data-name='wall_enable']").is(":checked");
       id_wall = $(this).attr('data-id-wall');
       if(selectionSubject !== subjectText & enableWall == true){
           selectionSubject = subjectText;
           sessionStorage.setItem("id_wall", id_wall);
           showInsertPublication();
           cleanPublications();
           publicationProcess(id_wall, id_user);
       }else {
            showAlert("Selecciono la misma materia o el muro esta deshabilitado", "alert-danger");
       } 
   });
   
   /**When the user clicked in the subject's checkbox the wall disable or enable*/
   $("#subject-list").on('click', 'input[data-name="wall_enable"]', function(){
       var enableWall = $(this).is(':checked');
       var subjectToModify = $(this).parents('span').siblings('a').attr('data-id-subject');
       var promiseModifyMuro = callAJAX('subject.do', {id_career:id_careerSelected, id_subject:subjectToModify, enable:enableWall}, 'POST');
       promiseModifyMuro
               .then(function(data){
                    if(!data.error){
                        showAlert(data.mensaje, "alert-success");
                        selectionSubject = "";
                        hideInsertPublication();
                        cleanPublications();
                    }else {
                        showAlert(data.error, "alert-danger");
                    }
                });
   });
   
   /**When the user press Enter in text or link publication to insert*/
    $("[data-action='input-publication']").on('keypress', function(event) {
        if(event.which == 13) {
            sendPublicationToInsert();
        }
    });

    /**When the user clicks in button send publication*/
    $("[data-action='send-publication']").on('click', function(){  
        sendPublicationToInsert();
    });

    /**When the user press Enter in text comment to insert*/
    $("#publications").on('keypress','.like-comment-panel input[name="textComment"]', function(event) {
        if(event.which == 13) {
           var element = $(this).siblings().find("[name='submitComment']");
           sendCommentToInsert(element);
        }
    });
    
    /**When the user clicks in button send comment*/
    $("#publications").on('click','.like-comment-panel input[name="submitComment"]', function(){
        var element = $(this);
        sendCommentToInsert(element);
    });
    
   /**When the user select one option in the publication's option menu
    * return false beacause are links and I do not want to reload the page
    * */
   $("#publications").on('click','article a[data-name="options"]', function(){
        var optionName = $(this).text();
        switch(optionName){
            case 'Eliminar': 
                  var publicationClass = $(this).parent().siblings('span').hasClass('publicationOptions');
                  var actionDelete = $(this).attr('data-action');
                  var id_publication = $(this).parents('article').attr('data-id-publication');
                  if(actionDelete == 'publication'){
                        deletePublication(id_publication);
                  }else{
                       var id_comment = $(this).parents('div .comment').attr('data-id-comment');
                       deleteComment(id_publication, id_comment);
                  }
                  break;
        }
        return false;
    });
    
    /**Update like in publiction when the user click in the button called 'Me gusta'*/
   $('#publications').on('click','button[name=like]', function() { 
        var id_publicationToInsert = $(this).parents('article').attr('data-id-publication');
        var countLikeElement = $(this).find('[data-name="countLike"]');
        var likeName = $(this).find('[data-name="likeName"]');
        var disableButton = $(this);
        var valueLike = parseInt(countLikeElement.text()) + 1;
        
       /**Insert like in the table and update like variable in the publication's table using trigger*/
        var promiseLike = callAJAX('publication.do', {method:'put', id_user:id_user, id_publication:id_publicationToInsert}, 'POST');
        promiseLike
                .then(function(vector){
                    if(!vector.error){
                        rankPublication(id_publicationToInsert);
                        likeName.text('Esto te gusta');
                        countLikeElement.text(valueLike);
                        disableButton.attr('disabled', true);
                    }else {
                        showAlert(vector.error, "alert-danger");
                    }
                });         
   });
   
   /**When the user clicks in facebook or twitter icon in one publicacion to share*/
    $("#publications").on('click','[data-share]', function(){
        var dataName = $(this).data('share');
        var id_publication = $(this).parents('article').attr('data-id-publication');
        var publicationText = $(this).parents('article').find('input[name="publicationText"]').val();
                
        if(dataName == 'facebook'){
            //function in FacebookAPI
            shareFacebookPublication(id_publication, id_user, publicationText);
            rankPublication(id_publication);
        }else{
            //function in TwitterAPI
            shareTwitterPublication(id_publication, id_user, publicationText);
            rankPublication(id_publication);
        }
        return false;
    });
    
    /**Insert new publication in the wall*/
    function sendPublicationToInsert(){
        var maxLength = 150;
        var contentText = $.trim($("#text-publication-to-insert").val());
        var contentLink = $.trim($("#link-publication-to-insert").val());
        var fullContentLink = (contentLink !== "") ? "^"+contentLink :"";
        var finalContent = contentText + fullContentLink;
        if(finalContent.length === 0 || finalContent.length > maxLength){
             showAlert("Contenido vacio o mayor a 150 caracteres", "alert-danger");
        }else {
            insertPublication(finalContent, id_wall, id_user);
        }
    };
    
    /**The process get id_muro then get and show the muro's publications*/
    function publicationProcess(id_wall, id_user){
        var promisePublication = callAJAX('publication.do', {id_wall:id_wall, id_user:id_user}); //AJAX return a promise
        promisePublication
                .then(function(vector){
                    if(!vector.error){
                        loadPublications(vector);                        
                    }else {
                        showAlert(vector.error, "alert-danger");
                        showInsertPublication();
                        
                    }
                });
    }
    
    function loadPublicationsAlert(vector){
        var $publication_count = $("#publication_count");
        var $publication_menu = $("#publication_menu");

        var count = parseInt($publication_count.text());
        var finalCount = isNaN(count) ? vector.length : count + vector.length; 
        $publication_count.text(finalCount);

        vector.forEach(function(publication){
            var $publication_item = $("<li><a href='#" + publication.id_publicacion + "'>" + publication.texto + "</a></li>");
            $publication_menu.append($publication_item);  
        });
    }
    
    /**Load publications in muro*/
    function loadPublications(vector){
        for(var i = 0; i < vector.length; i++) {
            var id_publication = vector[i].id_publicacion;
            var publicationText = vector[i].texto;
            var publicationLikes = vector[i].likes;
            var likeName = vector[i].userLike;
            var publicationIdUser = vector[i].id_usuario;
            var publicationUserName = vector[i].nombreUsuario;
            console.log(publicationUserName);
            var publicationDate = vector[i].fecha_publicacion;
            var isLike = (likeName == "Esto te gusta") ? "disabled": "";
            var isStudent = (id_role == 3 || id_role == 1)? "hide" : "";
            endDatePublication = publicationDate; //revisar, posible eliminacion
            
            var accessDate = Date.parse(lastAccessDate); 
            var pubDate = Date.parse(publicationDate); 
             
            var commentList = vector[i].commentList;
            var cantComments = 0;
            if(commentList !== null){
                cantComments = commentList.length;
            }   
            
            var result = publicationText.split('^');
            var contentText = result[0];
            var contentLink = (result[1] != null) ? result[1] : "";

            var content = detectContent(contentLink);
            
            $("#publications").prepend("<article data-id-publication='"+id_publication+"' id='"+id_publication+"'  class='publication clearfix'>\n\
                            <input type='hidden' name='publicationText' value='"+publicationText+"' >\n\
                                <div class='dropdown pull-right' >\n\
                                    <button class='btn btn-default btn-xs dropdown-toggle "+isStudent+"' type='button' id='optionsPublication' data-toggle='dropdown' aria-expanded='true'>\n\
                                    <span class='caret'></span></button>\n\
                                    <ul class='dropdown-menu dropdown-menu-right' role='menu' aria-labelledby='optionsPublication'>\n\
                                        <li role='presentation'><a role='item' href='#' data-name='options' data-action='publication'>Eliminar</a></li>\n\
                                    </ul>\n\
                                </div>\n\
                            <div class='publication-info'>\n\
                                <p><span> " + publicationDate + " </span> publicado por: <strong data-owner='owner'>" + publicationUserName +"</strong> </p>\n\
                            </div>\n\
                            <div>\n\
                                <p class='publication-comment'>"+contentText+"</p>\n\
                            </div>\n\
                            <div class='row'>"+content+"</div>\n\
                            <div class='like-comment-panel'>\n\
                                <div class='form-group'>\n\
                                    <button class='likePublication btn btn-primary btn-sm' type='button' name='like' "+isLike+" ><span data-name='likeName'>"+likeName+"</span> <span class='badge' data-name='countLike'>"+publicationLikes+"</span></button>\n\
                                    <a data-share='facebook'><img height='30' width='30' src='http://static.tumblr.com/r14jw9y/31Gnichxt/facebook.png' title='Facebook' ></a>\n\
                                    <a data-share='twitter' href='http://twitter.com/intent/tweet?text="+publicationText+"'><img height='30' width='30' src='http://static.tumblr.com/r14jw9y/JfEnichyk/twitter.png' title='Twitter'></a>\n\
                                </div>\n\
                                <div class='form-group'>\n\
                                    <div class='input-group col-md-8'>\n\
                                        <input type='text' class='form-control' name='textComment' placeholder='Ingrese comentario'>\n\
                                        <span class='input-group-btn'>\n\
                                            <input type='button' class='btn btn-primary' name='submitComment' value='Comentar'>\n\
                                        </span>\n\
                                    </div>\n\
                                </div>\n\
                            </div>\n\
                            <div class='comments-panel'>\n\
                                <a href='#button"+id_publication+"' class='btn btn-default btn-sm' data-toggle='collapse'>Comentarios <span class='badge' data-name='cant-comments'>"+cantComments+"</span></a>\n\
                                <div class='collapse' id='button"+id_publication+"'>\n\
                                    <div class='well'>\n\
                                        <ul class='comment-list'></ul>\n\
                                    </div>\n\
                                </div>\n\
                            </div>\n\
                        </article>");
              loadCommentsInPublication(commentList);
              if(accessDate <= pubDate){
                  changePublicationColor(id_publication);
              }
        }
    }


    /**Generate subjects list when the user clicks on career*/
    function generateSubjectList(id_careerSelected){
        var promiseSubject = callAJAX('subject.do', {id_career:id_careerSelected});
        return promiseSubject
                .then(function(vector){
                    if(!vector.error){
                        loadSubjectList(vector);
                    }else{
                        showAlert(vector.error, "alert-danger");
                    }
                });
    };

    /**Load subject list*/
    function loadSubjectList(vector){
        $("#subject-list").empty();
        for(var i = 0; i < vector.length; i++) {
            var id_subject = vector[i].id_materia;
            var subjectName = vector[i].nombre;
            var wallName = vector[i].muro;
            var id_wall = wallName.id_muro;
            var enable = wallName.habilitado;
            var checked = (enable) ? "checked" : "";

            var hideCheckbox = (id_role == 3 || id_role == 1) ? "hide" : "";
            var tableNone = (id_role == 3 || id_role == 1) ? "style='display: inline'" : "";

            $("#subject-list").append("<div class='input-group' "+tableNone+">\n\
                                        <span class='input-group-addon "+hideCheckbox+"'>\n\
                                            <label>\n\
                                                <input type='checkbox' data-name='wall_enable' "+checked+" > Habilitado\n\
                                            </label>\n\
                                        </span>\n\
                                        <a href='#' class='list-group-item form-control' data-id-subject='"+id_subject+"' data-id-wall='"+id_wall+"'>"+subjectName+"</a></div>");
        }
    }

    
    /**Insert publication in the actual muro*/
    function insertPublication(content,id_wall, id_user){
        var promiseInsert = callAJAX('publication.do', {text:content, id_wall: id_wall, id_user: id_user}, 'POST');
        promiseInsert.
                then(function(data){
                    if(!data.error){
                        cleanContentToInsert();
                        showAlert(data.mensaje, "alert-success");
                        getLast5Publications();
                    }else {
                        showAlert(data.error, "alert-danger");
                    }
                });
    }
    
    /**Insert new comment in one particular publication*/
    function sendCommentToInsert(element){
        var id_publication = $(element).parents('article').attr('data-id-publication');
        var input = $(element).parent().siblings('input[name=textComment]');
        var inputText = $.trim(input.val());
        var maxLength = 150;
        var countCommentsElement = $(element).parents('div').siblings('.comments-panel').find('[data-name="cant-comments"]');
        var lastDateComment = $(element).parents('div').siblings('.comments-panel').find('li:first').find(".date-comment").text();
       if(inputText.length === 0 || inputText.length > maxLength){
            showAlert("Contenido vacio o mayor a 150 caracteres", "alert-danger");
       }else {
            var promiseComment = callAJAX('comment.do', {text:inputText, id_user:id_user, id_publication:id_publication}, 'POST');
            promiseComment
                    .then(function(vector){
                            if(!vector.error){
                                input.val('');
                                console.log("comentario insertado...");
                                rankPublication(id_publication);
                                commentsListByPublication(id_publication, lastDateComment, countCommentsElement);
                            }else {
                                showAlert(vector.error, "alert-danger");
                            }
                        });
       }
    }
    
    /**Return the last 5 publication*/
    function getLast5Publications() {
        if(id_wall !== -1){
            var promiseTopPublications = callAJAX('publication.do', {id_wall:id_wall, endDatePublication:endDatePublication});
            promiseTopPublications
                    .then(function(vector){
                        if(!vector.error){
                            loadPublicationsAlert(vector);
                            loadPublications(vector);
                        }
                    });
        }        
    }
    
    /**Return comments list by publication */
    function commentsListByPublication(id_publication, lastDateComment, countCommentsElement){
        var promiseCommentsByPublication = callAJAX('comment.do', {id_publication:id_publication, lastDateComment:lastDateComment});
            promiseCommentsByPublication
                        .then(function(vector){
                            if(!vector.error){
                                loadCommentsInPublication(vector, countCommentsElement);
                            }
                            else {
                                showAlert(vector.error, "alert-danger");
                            }
                        });
    }
    
    /**Load comments in the selected publication*/
    function loadCommentsInPublication(vector, countCommentsElement){
        if(vector != null){
            for(var i = 0; i < vector.length; i++){
            var id_comment = vector[i].id_comentario;
            var text = vector[i].texto;
            var id_publication = vector[i].id_publicacion;
            var userName = vector[i].nombre+" "+vector[i].apellido;
            var commentDate = vector[i].fecha_creacion;
            var isStudent = (id_role == 3 || id_role == 1) ? "hide" : "";
            
            if(countCommentsElement != null){
                countCommentsElement.text( parseInt(countCommentsElement.text()) + vector.length);
            }
            
            $("article[data-id-publication='"+id_publication+"']").children(".comments-panel").find(".comment-list").prepend("\
            <li class='comment' data-id-comment='"+id_comment+"'>\n\
                <div class='dropdown pull-right'>\n\
                    <button class='btn btn-default btn-xs dropdown-toggle "+isStudent+"' type='button' id='optionsComment' data-toggle='dropdown' aria-expanded='true'>\n\
                        <span class='caret'></span>\n\
                    </button>\n\
                    <ul class='dropdown-menu dropdown-menu-right' role='menu' aria-labelledby='optionsComment'>\n\
                        <li role='presentation'><a role='item' data-action='comment' data-name='options' href='#'>Eliminar</a></li>\n\
                    </ul>\n\
                </div>\n\
                <div class='comment-info'>\n\
                    <p><strong data-owner='owner'>"+userName+"</strong><span> "+text+"</span></p>\n\
                    <p class='help-block date-comment'>"+commentDate+"</p>\n\
                </div>\n\
            </li>");
            }
        }
    }
    
});

/**Generate career list when the page load*/
function generateCareerList() {
    var promiseCareer = callAJAX('career.do');
    return promiseCareer
            .then(function (vector) {
                if (!vector.error) {
                    for (var i = 0; i < vector.length; i++) {
                        var id_career = vector[i].id_carrera;
                        var name = vector[i].nombre;
                        $("#career-list").append("<a href='#' class='list-group-item' data-name='"+name+"' data-id-career='"+id_career+"'>" + name + "</a>"); 
                    }
                } else {
                    showAlert(vector.error, "alert-danger");
                }
            });
}

/**Function that detect content in each publication*/
function detectContent(text) {
    var urlRegex = /(https?:\/\/[^\s]+)/g;
    var imageRegex = /\.(jpeg|jpg|gif|png)$/;
    var youtubeRegex = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var vimeoRegex = /vimeo\.com\/([0-9]{1,10})/;
    var oneLink = false;
    
    return text.replace(urlRegex, function(url) {
        if(!oneLink){
            if(url.match(imageRegex)){
                oneLink = true;
                var nameImage = url.substr(url.lastIndexOf("/"));
                return "<div class='col-md-5'>\n\
                            <div class='thumbnail'>\n\
                                <img src='img"+nameImage+"'>\n\
                            </div>\n\
                        </div>";
            }else if(url.match(vimeoRegex)){
                var match = url.match(vimeoRegex);
                if(match){
                    oneLink = true;
                    var id_vimeo = match[1];
                    return "<div class='col-md-6'>\n\
                                <div class='embed-responsive embed-responsive-4by3'>\n\
                                    <iframe class='embed-responsive-item' src='https://player.vimeo.com/video/"+id_vimeo+"' ></iframe>\n\
                                    <div class='caption'>\n\
                                        <p><a href='"+match+"' target='_blank'>Ver video en Vimeo</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>";
                }
            }else if(url.match(youtubeRegex)){
                var match = url.match(youtubeRegex);
                if(match){
                    oneLink = true;
                    var id_youtube = match[2];
                    return "<div class='col-md-6'>\n\
                                <div class='embed-responsive embed-responsive-4by3'>\n\
                                  <iframe class='embed-responsive-item' src='https://www.youtube.com/embed/"+id_youtube+"'></iframe>\n\
                                </div>\n\
                                <div class='caption'>\n\
                                    <p><a href='"+match+"' target='_blank'>Ver video en YouTube</a></p>\n\
                                </div>\n\
                            </div>";
                }
            }else {
                oneLink = true;
                return "<div class='col-md-5'>\n\
                            <p>Link a: <a href="+url+" target='_blank'>"+url+"</a></p>\n\
                        </div>";
            }
        }else{
            return "<div class='col-md-5'>\n\
                        <p>Link a: <a href="+url+" target='_blank'>"+url+"</a></p>\n\
                    </div>";
        }
    });
}

/**Delete the selected publication*/
function deletePublication(id_publication){
    var promiseDeletePublication = callAJAX('publication.do', {id_publication:id_publication, method:'delete'}, 'POST');
    promiseDeletePublication
            .then(function(data){
                if(!data.error){
                    showAlert(data.mensaje, "alert-success");
                    deleteDivPublication(id_publication);
                }else {
                    showAlert(data.error, "alert-danger");
                }
            });
}

/**Delete the selected comment in one publication*/
function deleteComment(id_publication, id_comment){
    var promiseDeleteComment = callAJAX('comment.do', {id_comment:id_comment, method:'delete'}, 'POST');
    promiseDeleteComment
            .then(function(data){
                if(!data.error){
                    showAlert(data.mensaje, "alert-success");
                    deleteDivComment(id_publication, id_comment);
                }else {
                    showAlert(data.error, "alert-danger");
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

//Show alert for error or success
function showAlert(message, alert_type) {
    $('#alert-error-success').append('<div class="alert '+ alert_type +' fade in alert-message" role="alert"><button class="close" data-dismiss="alert"><span>&times;</span></button><strong>'+ message +'</strong></div>');
    //$('#alert-error-success').focus();
    setTimeout(function() { // this will automatically close the alert and remove this if the users doesnt close it in 5 secs
        $(".alert-message").alert('close');
    }, 3000);
}

function getFormattedDate(dt){
    return dt.getDate() + "/" + (dt.getMonth() + 1) + "/" + dt.getFullYear() + " " +dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
}

function showInsertPublication(){
    $("#insert-form-publication").show();
}

function hideInsertPublication(){
    $("#insert-form-publication").hide();
}

function cleanPublications(){
    $("#publications").empty();
}

function cleanContentToInsert(){
    $("#text-publication-to-insert").val("");
    $("#link-publication-to-insert").val("");
}

function changePublicationColor(id_publication){
    $("#publications").find('article[data-id-publication="'+id_publication+'"]').css("background-color", "lightblue");
}

function rankPublication(id_publication){
    var topDOM = $("#publications").find('article[data-id-publication="'+id_publication+'"]');
    $('html, body').animate({ scrollTop: 0 }, 'slow');
    $("#publications").prepend(topDOM);
}

/**Remove publication of the DOM*/
function deleteDivPublication(id_publication){
    $("#publications").find('article[data-id-publication="'+id_publication+'"]').remove();
}

/**Remove comment of the DOM*/
function deleteDivComment(id_publication, id_comment){
    $("#publications").find('article[data-id-publication="'+id_publication+'"]').find('[data-id-comment="'+id_comment+'"]').remove();
}

/**Return error to callback ajax*/
function callback_error(XMLHttpRequest, textStatus, errorThrown){
    showAlert("Error Servlet: "+ XMLHttpRequest.status +" ,"+ textStatus +" ,"+ errorThrown, "alert-danger");
}
