package dousuario;

import br.ufrn.dimap.pairg.sumosensei.app.R;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

public class TaskEnviaEMail extends AsyncTask<String, String, Void> {

	@Override
	protected Void doInBackground(String... arg0) 
	{
		GMailSender sender = new GMailSender("sumosenseipairg@gmail.com", "viewtiful4490");
		String assuntoDoEmail = arg0[0];
		String corpoDoEmail = arg0[1];
		String quemEnviouOEmail = arg0[2];
		String emailDestinatario = arg0[3];
		
		
		try 
		{
			/*sender.sendMail("sua senha perdida",   
			        "sua senha é viewtiful4490",   
			        "fabioandrewsrochamarques@gmail.com",   
			        "fabioandrewsrochamarques@gmail.com");*/
			sender.sendMail(assuntoDoEmail,   
							corpoDoEmail,   
							quemEnviouOEmail,   
							emailDestinatario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
