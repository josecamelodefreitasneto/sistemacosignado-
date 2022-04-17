package gm.utils.image;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.date.Cronometro;
import gm.utils.exception.UException;

/*
 * depende da instala"+UConstantes.cedilha+""+UConstantes.a_til+"o do image magic no servidor
 * */

public class ImageResizeImageMagick extends ImageResize{
	
	public void execute() {
		try {
			ConvertCmd cmd = new ConvertCmd();
			IMOperation op = new IMOperation();
			op.addImage( getOrigem() );
			op.thumbnail(getLargura(),getAltura());
			op.addImage(getDestino());
			cmd.run(op);
		} catch (Exception e) {
			ULog.error("Verifique se o ImageMagic est"+UConstantes.a_agudo+" instalado no servidor");
			throw UException.runtime(e);
		}
	}
	
	public static void main(String[] args) {
		Cronometro cron = Cronometro.start();
		ImageResize novo = new ImageResizeImageMagick();
		novo.setOrigem("c:\\tmp\\imagem.jpg");
		novo.setDestino("c:\\tmp\\imagem_magic.jpg");
		novo.setLargura(200);
		novo.exec();
		Cronometro.print(cron, "ImageResizeImageMagick.main");
	}
	
}
