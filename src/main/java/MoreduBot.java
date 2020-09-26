import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class MoreduBot {

    public static JDA jda;
    public final static String PREFIX = "-";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            //el bot se conecta
            jda = JDABuilder.createDefault("NzU5MjIzNDMxMTAyMzMyOTQ5.X26X6w.4afb0s7idHPDsgMDqA7v4rMz6lY").build();


        } catch(LoginException le) {
            //error conectar bot
            le.printStackTrace();
        }


        //seteamos el estdo del bot
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        //seteamos actividad del bot
        jda.getPresence().setActivity(Activity.watching("Koreanas"));

        //añadimos los eventos
        jda.addEventListener(new Commands());
    }

}
