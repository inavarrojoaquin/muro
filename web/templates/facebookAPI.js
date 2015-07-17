window.fbAsyncInit = function() {
  FB.init({
    appId      : '102029326807536',
    xfbml      : true,
    version    : 'v2.3'
  });
};

(function(d, s, id){
   var js, fjs = d.getElementsByTagName(s)[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement(s); js.id = id;
   js.src = "//connect.facebook.net/en_US/sdk.js";
   fjs.parentNode.insertBefore(js, fjs);
 }(document, 'script', 'facebook-jssdk'));
    
function shareFacebookPublication(id_publication, id_user, publicationText){
    FB.ui(
      {
        method: 'stream.publish',
      attachment: {
          name: 'Compartir publicacion',
          description: publicationText,
          media:[{"type":"image","src":"http://files.softicons.com/download/web-icons/safari-4-web-apps-icons-by-parthiban-mohanraj/png/512/Facebook.png", 'href':'http://localhost:8080/muro'}]
        }
      },
      function(response) {
        if (response && response.post_id) {
            insertShare(id_publication, id_user, publicationText, 'F');
        }
      }
    );
}

/**Insert share in database*/
function insertShare(id_publication, id_user, publicationText, destination){
    var promiseInsertShare = callAJAX('share.do', {id_publication:id_publication, id_user:id_user, publicationText:publicationText, destination:destination}, 'POST');
    promiseInsertShare
            .then(function(data){
                if(!data.error){
                    console.log(data.mensaje);
                }else {
                    console.log(data.error);
                }
            });
}

/**Method responsible to call servlet and select, insert or update values*/
function callAJAX(url, parameters, type){
     return $.ajax({
            url: url,
            data: parameters,
            type: type,
            datType: 'json'
        });     
}