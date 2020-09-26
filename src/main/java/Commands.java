import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.awt.*;

public class Commands extends ListenerAdapter {
    private static final Random RANDOM = new Random();
    private static EmbedBuilder eb;
    //cada vez que ocurra un evento del tipo GuildMessageReceivedEvent se ejecutara
    //este metodo
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(!args[0].startsWith(MoreduBot.PREFIX))
            return;

        String val = args[0].toLowerCase();
        switch(val) {
            case "-info":
                //info del bot
                eb = new EmbedBuilder();
                eb.setTitle("Informacion de MoreduBot");
                eb.setDescription("Bot creado especialmente para el Eduardo para que vea sus Koreanas preferidas");
                eb.addField("Nota", "-help para ver los comandos", false);
                eb.addField("PD", "Debería estar avanzando en la tesis en vez de crear estas cosas :pensive:", false);
                eb.setColor(Color.yellow);
                eb.setFooter("Creado por QuiroD10s");
                //getChannel retorna el canal de texto del mensaje
                event.getChannel().sendMessage(eb.build()).queue();

                eb.clear();
                break;

            case "-frase":
            	//frases Célebres
                final String[] FRASES = {"Aguante el :regional_indicator_c::regional_indicator_o::regional_indicator_n::regional_indicator_c::regional_indicator_e:"
                        , "Me gustan las coreanas", "El Conce es mi pasión", "Yaaa no sabe nada"
                        , "SABES QUE SOY UN FARSANTEEEE :notes:", "jeje ta bien man :ok_hand:", "¿Si pido comida a las 10 me la traen?"
                        , "Ese es mi man :muscle:", "¿Que pasoooo maaaan? :smile_cat:"};
                final int NUM_FRASES = FRASES.length;
                int n = RANDOM.nextInt(NUM_FRASES);
                event.getChannel().sendMessage("Frase de Eduardito #" + (n + 1) + ": " + FRASES[n]).queue();
                break;

            case "-buscar":
            	//query que devulve un gif de acuerdo al tema
                String query = "";
                //se crea el cliente
                OkHttpClient client = new OkHttpClient();
                for (int i = 1; i < args.length; i++) {
                    query += args[i].toLowerCase() + "+";
                }

                //la api de giphy permite elegir desde donde entregar los resultados
                final int LIMITE = 500;
                int offset = RANDOM.nextInt(LIMITE);

                //creamos la query
                Request request = new Request.Builder().url("https://api.giphy.com/v1/gifs/search?api_key=zSn7z1C9tEIx0pSrxCkIj24RBE99nKHj&q="
                        + query.substring(0, query.length()-1) + "&limit=10&offset=" + offset).build();

                Call call = client.newCall(request);

                try {
                    //ejecutamos la query
                    Response response = call.execute();
                    //respuesta
                    String respuesta = response.body().string();
                    //la respuesta la transformamos a JSON para que sea mas facil procesarla
                    JSONObject json = new JSONObject(respuesta);
                    JSONArray arrayjson = json.getJSONArray("data");

                    //seleccionamos un resultado random entregado por la api de giphy
                    event.getChannel().sendMessage(arrayjson.getJSONObject(RANDOM.nextInt(10)).get("url").toString()).queue();

                } catch (IOException e) {
					event.getChannel().sendMessage("Error al establecer conexión :sob:").queue();
                    e.printStackTrace();
                } catch(JSONException j) {
                    event.getChannel().sendMessage("No encontré nada man :sob:").queue();
                    e.printStackTrace();
                }
                break;

            case "-help":
            	//comandos de ayuda
                eb = new EmbedBuilder();
                eb.setTitle("Comandos");
                eb.addField("-info", "información totalmente inútil del bot y su creador", false);
                eb.addField("-frase", "frase célebre de Eduardo", false);
                eb.addField("-buscar X", "busca un gif sobre el tema X", false);
                eb.setColor(Color.blue);
                event.getChannel().sendMessage(eb.build()).queue();

                eb.clear();

        }

    }

}
