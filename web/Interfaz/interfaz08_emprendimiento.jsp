<%-- 
    Document   : interfaz08_emprendimiento
    Created on : 13/03/2015, 04:35:26 PM
    Author     : cpe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vaalsoft</title>

        <meta charset="utf-8">
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

        <!--Link calendaro-->
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <link rel="stylesheet" type="text/css" href="css/calendario.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/calendario.js"></script>
        <script type="text/javascript">

            $(function () {
                $("#fecha3").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    showOn: "button",
                    buttonImage: "css/images/ico.png",
                    buttonImageOnly: true,
                    showButtonPanel: true,
                })
            })
            $(function () {
                $("#fecha4").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    showOn: "button",
                    buttonImage: "css/images/ico.png",
                    buttonImageOnly: true,
                    showButtonPanel: true,
                })
            })
            < /head>


        </script>

    <body background="Iconos/fondo.png">

        <div id="bannerA">
            <link"=blue" vlink="blue" alink="blue">


            <!--Titulo-->
            <center><legend><font color="#0B610B"><h1></h1></legend></font></center>
            <img src="Iconos/logocoomeva1.png">
            <a href="http://softwarevaalsoft.wix.com/vaalsoft"  target="_blank" title="Visita nuestra pagina y contactenos!"><img src="Iconos/vaalsoft.png" align="right" alt="nombre" border="0"></a> 

        </div>
        <!--tabla de menu-->

        <div id="bannerI">
            <br>
            <br>
            <center><a href="interfaz02_bienvenido.html" title="Inicio"><img src="Iconos/house.png" ></a>
                <a href="interfaz13_ambiental.html" title="Medio Ambiente"><img src="Iconos/tree.png"></a>
                <a href="https://www.facebook.com/vaalsoft?ref=ts&fref=ts" title="Ayuda!" target="_blank"><img src="Iconos/info.png"></a></center>

            <center><h3>Menu</h3></center>
            <!--Menu y botones con vinculos-->
            <div id='cssmenu'>
                <ul>
                    <li class='last'><a href='interfaz03_consultarcliente.html'><span>Consultar cliente</span></a></li>
                    <li class='has-sub'><a href=''><span>Registrar cliente</span></a>
                        <ul>
                            <li ><a href='interfaz07_fortalecimiento.html'><span>Fortalecimiento</span></a></li>
                            <li class='active'><a href='interfaz08_emprendimiento.html'><span>Emprendimiento</span></a></li>

                        </ul>
                    </li>
                    <li class='last'><a href='interfaz12_registrar_administrador.html'><span>Registrar usuario</span></a></li>
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


    <center><table border="0" bgcolor=""  div id="tablaregistrocliente">
            <font face=" Berlin Sans FBBerlin Sans FB"><h1>Registrar cliente <br> Idea de emprendimiento</h1></font>
            <p>
                <br>

                <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>
                        <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>

            <tr><td>
            <center><font size="4" face=" Berlin Sans FBBerlin Sans FB">Datos personales:</font><hr/></center>
            <td>
                <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>


            <tr><td>
                    <label>Nombre: *</label>
                <td>
                    <label>Apellido: *</label>
            <tr><td>
                    <input type="text"  placeholder="" maxlength="40" required autofocus><br>
                <td>
                    <input type="text"  placeholder="" maxlength="40" required><br>


                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>



            <tr><td>
                    <label>Cedula de ciudadania: *</label>
                <td>
                    <label>Genero *</label>
            <tr><td>
                    <input type="number" required placeholder="" maxlength="10" required><br>
                <td>
                    <select name="genero" Id="genero">
                        <option selected="selected"></option>
                        <option>Masculino</options>
                        <option>Femenino</options>
                    </select><br>


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
                    <input type="number"  placeholder="" maxlength="15" required><br>
                <td>
                    <input type="number"  placeholder="" maxlength="15" required><br>


                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>



            <tr><td>
                    <label>Direccion: *</label>
                <td>
                    <label>Ciudad: *</label>
            <tr><td>
                    <input type="text"  placeholder="" maxlength="20" required><br>
                <td>
                    <input type="text"  placeholder="" maxlength="20" required><br>


                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>



            <tr><td>
                    <label>Correo electronico: *</label>
                <td>
                    <label>Fecha de nacimiento: *</label>
            <tr><td>
                    <input type="email"  placeholder="" maxlength="450" required<br>
                <td>
                    <input type="text" name="fecha3" id="fecha3"><br/>

                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>



                        <!--Formulario idea de fortalecimiento-->
            <tr><td>
                    <label>Idea de negocio</label>
            <tr>
                <td> 
                    <textarea name="comentarios" rows="8" cols="30" placeholder="Describa aqui su idea de negocio......."></textarea>

                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>


            <tr><td>
                    <label>Monto requerido: *</label>
            <tr>
                <td>
                    <input id="campo2" name="Direccion" required=""  type="number" placeholder= "Digite el monto..."/><br>


                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>



            <tr><td>
                    <label>Fecha: *</label>
            <tr>
                <td>
                    <input id="fecha4" name="fecha4" required=""  type="text" placeholder= ""/>


                    <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>

            <tr><td>
                    <br><br><br><br><br><br><br>
            <center><input type="submit" div id="botonconsultar" nombre="submit" value="Registrar codeudor" ></center>
            <td><br><br><br><br><br><br><br>
            <center><input type="submit" div id="botonconsultar" value="Registrar"></center>

            <!--SALTO DE TABLA-->
            <tr><td>
                    <P>
                <td>
                    <P>


        </table></center>



</table>
</center>
</form>



</body>
</html>
