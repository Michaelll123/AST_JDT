package xmlutil;

import java.io.StringReader;
import java.io.StringWriter;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
 
import org.apache.log4j.Logger;

public class JaxbUtil {
	private static Logger logger = Logger.getLogger(JaxbUtil.class);
	private static JAXBContext jaxbContext;
	
	//xmlתjava����
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xml,Class<T> c){
		T t = null;
		try {
			jaxbContext = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			logger.info(e.getMessage());
		}
		return t;
	}
	
	//java����תxml
	public static String beanToXml(Object obj){
		StringWriter writer = null;
		try {
			jaxbContext=JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			//Marshaller.JAXB_FRAGMENT:�Ƿ�ʡ��xmlͷ��Ϣ,trueʡ�ԣ�false��ʡ��
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			//Marshaller.JAXB_FORMATTED_OUTPUT:�����Ƿ���ת����xmlʱͬʱ���и�ʽ����������ǩ�Զ����У�������һ�е�xml��
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//Marshaller.JAXB_ENCODING:xml�ı��뷽ʽ
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			writer = new StringWriter();
			marshaller.marshal(obj, writer);
		} catch (JAXBException e) {
			logger.info(e.getMessage());
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + writer.toString();
	}

}
