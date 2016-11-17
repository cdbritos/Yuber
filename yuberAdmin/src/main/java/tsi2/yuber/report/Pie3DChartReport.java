package tsi2.yuber.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import tsi2.yuber.admin.enums.TipoVerticalEnum;

public class Pie3DChartReport extends AbstractReport{



   private JRDataSource createDataSource() {
	   
	   
		DRDataSource dataSource = new DRDataSource("fecha", "ganancia");

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(getUrl());
		
		try {
			HttpResponse response = client.execute(request);
					
			TypeToken<List<DataReportGanancias>> token = new TypeToken<List<DataReportGanancias>>(){};
			List<DataReportGanancias> dataList = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), token.getType());

			for (DataReportGanancias item : dataList) {			
				dataSource.add(item.fecha, item.ganancia);
			}
			
			 
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataSource;
   }

	public Pie3DChartReport(OutputStream os, String vertical, TipoVerticalEnum tve, String tipoReporte) {
		  super(vertical,tve,tipoReporte);
	      build(os);
   }
	
	private void build(OutputStream os) {
		
		      FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		      TextColumnBuilder<String> fechaColumn = col.column("Fecha", "fecha", type.stringType());
		      TextColumnBuilder<Double> gananciaColumn = col.column("Ganancias", "ganancia", type.doubleType());
		      Templates.TVE = this.tve.name();
		      
		      try {
		    	      	  
		    	  DynamicReports.report()
		            .setTemplate(Templates.reportTemplate)
		            .columns(fechaColumn, gananciaColumn)
		            .title(Templates.createTitleComponent("Reporte de Ganancia Mensuales"))
		            .summary(
		                 cht.pie3DChart()
		                  .setTitle("Reporte de Ganancias Mensuales")
		                  .setTitleFont(boldFont)
		                  .setKey(fechaColumn)
		                  .series(
		                     cht.serie(gananciaColumn)))
		            .pageFooter(Templates.footerComponent)
		            .setDataSource(createDataSource())
		            .toPdf(os);	    	  
		
		      } catch (DRException e) {
		
		         e.printStackTrace();
		
		      }
		
		   }
	

	class DataReportGanancias {

		private String fecha;
		private Double  ganancia;
		
		public String getFecha() {
			return fecha;
		}
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		public Double getGanancia() {
			return ganancia;
		}
		public void setGanancia(Double ganancia) {
			this.ganancia = ganancia;
		}
	}

	

	
}
