<%-- 
    Document   : interfaz12_registrar_administrador
    Created on : 13/03/2015, 03:27:27 PM
    Author     : cpe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content=width=device-width, minium-scale=1, maximun-scale=1 />

        <LINK REL="StyleSheet" HREF="css/estilo.css" TYPE="text/css" MEDIA=screen>

        <!--Estilos del menu desplegable-->
        <link rel="shortcut icon"  href="Iconos/vaalsoftlogo.png">
        <meta charset='utf-8'>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="styles.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="JS/script.js"></script>
        <title>Vaalsoft</title>

    </head>
    <body background="Iconos/fondo.png">

        <div id="bannerA">
            <link vlink="blue" alink="blue">


            <!--Titulo-->
            <center><legend><font color="#0B610B"><h1></h1></legend></font></center>
            <img src="Iconos/logocoomeva1.png">
            <a href="http://softwarevaalsoft.wix.com/vaalsoft"  target="_blank" title="Visita nuestra pagina y contactenos!">
                <img src="Iconos/vaalsoft.png" align="right" alt="nombre" border="0"></a> 

        </div>

        <!--tabla de menu-->

        <div id="bannerI">
            <br>
            <br>
            <center><a href="interfaz02_bienvenido.html" title="Inicio"><img src="Iconos/house.png" ></a>
                <a href="interfaz13_ambiental.html" title="Medio Ambiente"><img src="Iconos/tree.png"></a>
                <a href="https://www.facebook.com/vaalsoft?ref=ts&fref=ts" title="Ayuda!" target="_blank">
                    <img src="Iconos/info.png"></a></center>

            <center><h3>Menu</h3></center>

            <!--Menu y botones con vinculos-->
            <div id='cssmenu'>
                <ul>
                    <li class='last'><a href='interfaz03_consultarcliente.html'><span>Consultar cliente</span></a></li>
                    <li class='has-sub'><a href=''><span>Registrar cliente</span></a>
                        <ul>
                            <li><a href='interfaz07_fortalecimiento.html'><span>Fortalecimiento</span></a></li>
                            <li><a href='interfaz08_emprendimiento.html'><span>Emprendimiento</span></a></li>

                        </ul>
                    </li>
                    <li class='active'><a href='interfaz12_registrar_administrador.html'><span>Registrar usuario</span></a></li>
                    <li class='last'><a href=''><span>Ayuda</span></a></li>
                    <li class='last'><a href='interfaz01_inicio.html'><span>Salir</span></a></li>
                </ul>
            </div>
        </div>


        <form action="sebas1.html" method="post" id="for"/>

        <!--consultar cliente -->
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>

    <center><table bgcolor="" border="0" div id="tablaconsultar"><center>

                <font face=" Berlin Sans FBBerlin Sans FB"><h1>Registrar administrador</h1></font>
                <center><table border="0" bgcolor="" style="width:100">


                        <tr><td>
                                <label id="nombreEmpleado "> Nombre: *</label>
                                
                            <td>
                                <label id="apellidoEmpleado">Apellido: *</label>
                        <tr><td>
                                <input type="text"  placeholder="Ingrese nombre..." maxlength="40" required autofocus><br>
                            <td>
                                <input type="text"  placeholder="Ingrese apellido..." maxlength="40" required><br>


                                <!--SALTO DE TABLA-->
                        <tr><td>
                                <P>
                            <td>
                                <P>




                        <tr><td>
                                <label>Cédula: *</label>
                            <td>
                                <label>Regional *</label>
                        <tr><td>
                                <input type="number" required placeholder="Ingrese cedula..." maxlength="10" required><br>
                            <td>
                                <select name="seleccioneciudad" Id="seleccioneciudad" placeholder="Seleccione ciudad...">
                                    <option  selected="selected"></option>
                                    <option>Cali</options>
                                    <option>Bogota</options>
                                    <option>Medellin</options>
                                    <option>Caribe</options>
                                    <option>Eje Cafetero</options>
                                    <option>Palmira</options>
                                    <option>Otros</options>

                                        <!--SALTO DE TABLA-->
                                    <tr><td>
                                            <P>
                                        <td>
                                            <P>



                                    <tr><td>
                                            <label>Telefono fijo: </label>
                                        <td>
                                            <label>Telefono celular: *</label>
                                    <tr><td>
                                            <input type="number"  placeholder="Ingrese telefono fijo..." maxlength="15" required><br>
                                        <td>
                                            <input type="number"  placeholder="Ingrese telefono celular..." maxlength="15" required><br>


                                            <!--SALTO DE TABLA-->
                                    <tr><td>
                                            <P>
                                        <td>
                                            <P>


                                    <tr><td>
                                            <label>Correo electronico: *</label>
                                    <tr><td>
                                            <input type="email" required placeholder="Ingrese correo..." maxlength="450"><br>


                                            <!--SALTO DE TABLA-->
                                    <tr><td>
                                            <P>
                                        <td>
                                            <P>


                                    <tr><td>
                                    <center><input type="submit" div id="botonconsultar" nombre="submit" value="Generar contraseña" ></center>
                                    <tr><td>
                                            <input type="password" required placeholder="Contrasena generada" maxlength="450"><br>
                                    <tr><td>
                                            <br>
                                    <center><input type="submit" div id="botonconsultar" value="Registrar"></center>

                    </table></center>



        </table>
    </center>
</form>




</body>
</html>
