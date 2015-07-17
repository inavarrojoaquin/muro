<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>NavBar</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>   <%--Boostrap css--%>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body id="top-page">
        <header>
            <nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
                <div class="container">
                    <div class="navbar-header">
                        <a href="#" class="navbar-brand">Muro <span class="small">ubp</span></a>
                    </div>
                    <%-- Inicia Menu --%>
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li role="presentation"><a href="#top-page">Inicio</a></li>
                            <li role="presentation"><a href="#">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        
        <section class="main container">
            <div class="row">
                <aside class="col-md-3">
                    <div class="list-group">
                        <a href="#" class="list-group-item list-group-item-heading active">Carreras</a>
                        <div>
                            <a href="#" class="list-group-item">Ingenieria Informatica</a>
                            <a href="#" class="list-group-item">Ingenieria en Telecomunicaciones</a>
                        </div>
                    </div>
                    <div class="list-group">
                        <a href="#" class="list-group-item list-group-item-heading active">Materias</a>
                        <div class="">
                            <div class="input-group">
                               <span class="input-group-addon">
                                   <label>
                                       <input type="checkbox"> Habilitado
                                   </label>
                               </span>
                               <a href="#" class="list-group-item form-control">DAS</a>
                            </div><!-- /input-group -->
                            <div class="input-group">
                               <span class="input-group-addon">
                                    <label>
                                        <input type="checkbox"> Habilitado
                                    </label>
                               </span>
                               <a href="#" class="list-group-item form-control">PDC</a>
                            </div><!-- /input-group -->
                        </div>    
                    </div>
                </aside>
                
                <section class="publications col-md-7">
                    <article class="publication clearfix">
                        <input type='hidden' name='publication'>
                        <input type='hidden' name='publicationText'>
                        <div class="dropdown pull-right">
                            <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="optionsComment" data-toggle="dropdown" aria-expanded="true">
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="optionsComment">
                                <li role="presentation"><a role="item" href="#">Eliminar</a></li>
                            </ul>
                        </div>
                        <div class="publication-info">
                            <p><span> 15/07/2015 </span> publicado por: <strong data-owner="owner">Febo</strong> </p>
                        </div>
                        <div>
                            <p class="publication-comment">Este es el comentario de la publicacion</p>
                        </div>
                        <div class="row">
                            <div class="col-md-5">
                                <div class='thumbnail'>
                                    <img src="http://imagenestodo.com/wp-content/uploads/2014/08/tumblr_m7fkokA0V11rxfqo7o1_5001.jpg">
                                    <div class="caption">
                                        <a href="http://imagenestodo.com/wp-content/uploads/2014/08/tumblr_m7fkokA0V11rxfqo7o1_5001.jpg">Link a la foto</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class='thumbnail'>
                                    <img src="http://imagenestodo.com/wp-content/uploads/2014/08/tumblr_m7fkokA0V11rxfqo7o1_5001.jpg">
                                    <div class="caption">
                                        <a href="http://imagenestodo.com/wp-content/uploads/2014/08/tumblr_m7fkokA0V11rxfqo7o1_5001.jpg">Link a la foto</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="">
                            <div class="form-group">
                                <button class='likePublication btn btn-primary btn-sm' type='button' name='like' value='Me gusta'>Me gusta <span class="badge">5</span></button>
                                <a data-share='facebook'><img height='30' width='30' src='http://static.tumblr.com/r14jw9y/31Gnichxt/facebook.png' title='Facebook'/></a>
                                <a data-share='twitter' href='http://twitter.com/intent/tweet?text="+publicationText+"'><img height='30' width='30' src='http://static.tumblr.com/r14jw9y/JfEnichyk/twitter.png' title='Twitter'/></a>
                            </div>
                            <div class='form-group'>
                                <div class='input-group col-md-8'>
                                    <input type='text' class='form-control' name='textComment' placeholder='Ingrese comentario'>
                                    <span class='input-group-btn'>
                                        <input type='button' class='btn btn-primary' name='submitComment' value='Comentar'>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="comments-panel">
                            <a href="#comments" class="btn btn-default btn-sm" data-toggle="collapse">Comentarios <span class="badge">5</span></a>
                            <div class="collapse" id="comments">
                                <div class="well">
                                    <ul>
                                        <li class="comment">
                                            <div class="dropdown pull-right">
                                                <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="optionsComment" data-toggle="dropdown" aria-expanded="true">
                                                    <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="optionsComment">
                                                    <li role="presentation"><a role="item" href="#">Eliminar</a></li>
                                                </ul>
                                            </div>
                                            <div class="comment-info">
                                                <p><strong data-owner="owner">Febo</strong><span> Comentario de la publicacion</span></p>
                                                <p class="help-block date-comment">15/07/2015</p>
                                            </div>
                                        </li>
                                        <li class="comment">
                                            <div class="dropdown pull-right">
                                                <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="optionsComment" data-toggle="dropdown" aria-expanded="true">
                                                    <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="optionsComment">
                                                    <li role="presentation"><a role="item" href="#">Eliminar</a></li>
                                                </ul>
                                            </div>
                                            <div class="comment-info">
                                                <p><strong data-owner="owner">Febo</strong><span> Comentario de la publicacion</span></p>
                                                <p class="help-block date-comment">15/07/2015</p>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </article>
                </section>
            </div>
        </section>
               
                    
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="alert alert-success" role="alert">
                        <button class="close" data-dismiss="alert"><span>&times;</span></button>
                        <strong>Esto es un mensaje</strong>
                    </div>
                </div>
            </div>    
        </div>
            
        
        
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script> <%--Boostrap js--%>
        <script src="js/script.js"  type="text/javascript"></script>
        
        <script>
                $(".publication").on('click', 'span > a', function(){
                    alert($(this).text());
                    return false; //al poner return false, el atributo href queda deshabilitado
                });
        </script>
    </body>
</html>
