package util;

public class Path {
    public static String ip = "http://localhost:8000/";

    //Cadastro Aluno Com traino e medidas ou so do professor
    public static String urCadastroUsuarios = ip + "personalgamer/users";

    //Listagem de Usuários
    public static String urUsuarios = ip + "personalgamer/users";

    //Get Usuario
    public static String urUsuario = ip + "personalgamer/users";

    //Update Measures/id
    public static String urlUpdateMeasures = ip + "personalgamer/users/measures/";

    //Update Measures/id
    public static String urlUpdateTraning = ip + "personalgamer/users/traning/";

    //Update Measures/id
    public static String urlUpdateUsers = ip + "personalgamer/users/";

    //Delete Usuario/id
    public static String urlDeleteMeasures = ip + "personalgamer/users/";

    //Get QRCode/id
    public static String urQRCode = ip + "personalgamer/QRCODE/";
}
