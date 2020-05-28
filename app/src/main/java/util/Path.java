package util;

public class Path {
    public static String ip = "http://192.168.1.8:8000/";

    //Cadastro Aluno Com traino e medidas ou so do professor
    public static String urlCadastroUsuarios = ip + "personalgamer/users";

    //Listagem de Usuários
    public static String urlUsuarios = ip + "personalgamer/users";

    //Get Usuario
    public static String urlGetUsuario = ip + "personalgamer/users/5eceaa62982d34e0922285f5";

    //Update Measures/id
    public static String urlUpdateMeasures = ip + "personalgamer/users/measures/5eceaa62982d34e0922285f5";

    //Update traning/id
    public static String urlUpdateTraning = ip + "personalgamer/users/traning/";

    //Update User/id
    public static String urlUpdateUsers = ip + "personalgamer/users/";

    //Delete Usuario/id
    public static String urlDeleteMeasures = ip + "personalgamer/users/";

    //Get qrcode/id
    public static String urQRCode = ip + "personalgamer/QRCODE/";
}
