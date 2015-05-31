$(document).ready(function(){
    $("#carreras").append("<ul></ul");
    getValues('career.do', '', function(vector){    
        $("#carreras ul").hide();
        for(i = 0; i < vector.length; i++) {
            var name = vector[i].nombre;
            $("#carreras ul").append("<li>"+name+"</li>");
        }
    });
});

$(document).ready(function(){
    var selectionCareer = "";
    var flag = false;
    
    $("#idCarrera").on("click", function(){
        $("#carreras ul").show();
    });
    
    $("#carreras").on("click", "li", function(){
       $(".error").empty();
       if (selectionCareer !== $(this).text()){
        selectionCareer = $(this).text();
        if(!flag){
            $("#materias").append("<ul></ul>");
            flag = true;
        }
        getValues('subject.do', 'name='+selectionCareer, function(vector){
            $("#materias ul").empty();
            for(i = 0; i < vector.length; i++) {
                var name = vector[i].nombre;    
                $("#materias ul").append("<li>"+name+"</li>");
            }
        });
      }else {
          $(".error").append("<span>La carrera ya fue seleccionada</span>");
      }
   });
});
/**Como Ajax es asincrono y necesito retornar el vector, uso la funcion my_callback donde le paso el vector como parametro.
 * No es recomedable usar la propiedad async: false
 * De esta forma, al devolver los datos, podemos trabajarlos en otra funcion y desaclopar el codigo
 * */
function getValues(url, parameter, my_callback){
    $.ajax({
        url: url,
        data: parameter,
        type: 'GET',
        datType: 'json',
        beforeSend: function(){},
        success: function(data){
            if(data !== ""){
                var vector = JSON.parse(data);
                my_callback(vector);
            }else{
                $(".error").append("<span>Error, no se encontraron materias</span>");
            }
        },
        error: callback_error,
        complete: function(){} //equivalente al always, siempre se ejecuta, sea success o fail
    });     
}

function callback_error(XMLHttpRequest, textStatus, errorThrown){
    $(".error").append("<span>Error Servlet: "+ XMLHttpRequest.status +"</span>");
}




