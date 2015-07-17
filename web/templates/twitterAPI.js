//window.twttr=(function(d,s,id){
//    var t,js,fjs=d.getElementsByTagName(s)[0];
//    if(d.getElementById(id)){return}js=d.createElement(s);
//    js.id=id;js.src="https://platform.twitter.com/widgets.js";
//    fjs.parentNode.insertBefore(js,fjs);
//    return window.twttr||(t={_e:[],ready:function(f){t._e.push(f);}});
//}(document,"script","twitter-wjs"));
window.twttr = (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0],
    t = window.twttr || {};
  if (d.getElementById(id)) return t;
  js = d.createElement(s);
  js.id = id;
  js.src = "https://platform.twitter.com/widgets.js";
  fjs.parentNode.insertBefore(js, fjs);
 
  t._e = [];
  t.ready = function(f) {
    t._e.push(f);
  };
 
  return t;
}(document, "script", "twitter-wjs"));

function shareTwitterPublication(id_publication, id_user, publicationText){
    insertShare(id_publication, id_user, publicationText, 'T');
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


///**This function to execute when the user send the tweet and call method that save data in database*/
//function handleTweetEvent(event){
//    if (event) {
//        //callAjax
//      console.log('Tweet enviado...');
//    }
//}
//
//twttr.ready(function(twttr){
//    twttr.events.bind('tweet', handleTweetEvent);
//});