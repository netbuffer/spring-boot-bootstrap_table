//package cn.com.ttblog.spring_boot_bootstrap_table.viewresolver;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.View;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.view.xml.MarshallingView;
//import java.util.Locale;
//
///**
// * View resolver for returning XML in a view-based system.
// */
//public class MarshallingXmlViewResolver implements ViewResolver {
//
//	private Marshaller marshaller;
//
//	@Autowired
//    public MarshallingXmlViewResolver(Marshaller marshaller) {
//		this.marshaller = marshaller;
//	}
//
//	@Override
//	public View resolveViewName(String viewName, Locale locale)
//			throws Exception {
//		MarshallingView view = new MarshallingView();
//		view.setMarshaller(marshaller);
//		return view;
//	}
//}