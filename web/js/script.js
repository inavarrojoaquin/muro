$(document).ready(function(){
    var selectionCareer = "";
    var selectionSubject = "";
    var id_careerSelected;
    var id_subjectSelected;
    var flag = false;
    
    $("#carreras").append("<ul></ul");
    getValues('career.do', '', function(vector){    
        $("#carreras ul").hide();
        for(var i = 0; i < vector.length; i++) {
            var id_career = vector[i].id_carrera;
            var name = vector[i].nombre;
            $("#carreras ul").append("<li><input type='hidden' name='"+name+"' value='"+id_career+"' />" +name+ "</li>");
        }
    });
    
    $("#idCarrera").on("click", function(){
        $("#carreras ul").show();
    });
    
    $("#carreras").on("click", "li", function(){
       cleanError();
       if (selectionCareer !== $(this).text()){
        selectionCareer = $(this).text();
        id_careerSelected = $("input[name='"+selectionCareer+"']").val();
        if(!flag){
            $("#materias").append("<ul></ul>");
            flag = true;
        }
        getValues('subject.do', 'name='+selectionCareer, function(vector){
            $("#materias ul").empty();
            for(var i = 0; i < vector.length; i++) {
                var id_subject = vector[i].id_materia;
                var name = vector[i].nombre;    
                $("#materias ul").append("<li><input type='hidden' name='"+name+"' value='"+id_subject+"' />" +name+ "</li>");
            }
        });
      }else {
            setError("<span>La carrera ya fue seleccionada</span>");
      }
   });
   
   $("#materias").on("click", "li", function(){
       cleanError();
       if(selectionSubject !== $(this).text()){
           selectionSubject = $(this).text();
           id_subjectSelected = $("input[name='"+selectionSubject+"']").val();
            getValues("wall.do", "id_career="+id_careerSelected+"&id_subject="+id_subjectSelected, function(vector){
                for(var i = 0; i < vector.length; i++){
                    var id_wall = vector[i].id_muro;
                    $("#id_muro").text("Id_muro: "+id_wall);
                }
            });
       }else {
            setError("<span>La materia ya fue seleccionada</span>");
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
                setError("<span>Error, no se encontraron materias</span>");
            }
        },
        error: callback_error,
        complete: function(){} //equivalente al always, siempre se ejecuta, sea success o fail
    });     
}

function setError(error){
    $(".error").append(error);
}

function cleanError(){
    $(".error").empty();
}

function callback_error(XMLHttpRequest, textStatus, errorThrown){
    setError("<span>Error Servlet: "+ XMLHttpRequest.status +"</span>");
}

/** Ejemplos de acceso
 * <input type="hidden" id="foo" name="zyx" value="bar" />

    alert($('input#foo').val());
    alert($('input[name=zyx]').val());
    alert($('input[type=hidden]').val());
    alert($(':hidden#foo').val());
    alert($('input:hidden[name=zyx]').val());
 * */




