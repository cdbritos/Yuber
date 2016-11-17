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

public class StackedBar3DChartReport extends AbstractReport {

   private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("user", "review", "cantServ");

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(getUrl());
		
		try {
			HttpResponse response = client.execute(request);
			
			TypeToken<List<DataReportReview>> token = new TypeToken<List<DataReportReview>>(){};
			List<DataReportReview> dataList = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), token.getType());

			for (DataReportReview item : dataList) {
				dataSource.add(item.name, item.reviewPromedio, item.cantidadServicios);
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

	public StackedBar3DChartReport(OutputStream os, String vertical, TipoVerticalEnum tve, String tipoReporte) {
		  super(vertical, tve, tipoReporte);
	      build(os);
   }
	
	private void build(OutputStream os) {
		
		      FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		      TextColumnBuilder<String> userColumn = col.column(getUsuario(), "user", type.stringType());
		      TextColumnBuilder<Double> reviewPromedioColumn = col.column("Review Promedio", "review", type.doubleType());
		      TextColumnBuilder<Integer> cantServiciosColumn = col.column("Cantidad Servicios", "cantServ", type.integerType());
		
		      try {
		    	      	  
		    	  DynamicReports.report()
		            .setTemplate(Templates.reportTemplate)
		            .columns(userColumn, reviewPromedioColumn, cantServiciosColumn)
		            .title(Templates.createTitleComponent("Reporte de Reviews para " + getUsuario()))
		            .summary(
		            		 cht.stackedBar3DChart()
       		                   .setTitle("Grafica Reviews/Cantidad Servicios")
       		                   .setTitleFont(boldFont)
       		                   .setCategory(userColumn)
       		                   .series(
       		                      cht.serie(reviewPromedioColumn), cht.serie(cantServiciosColumn))
       		                   .setCategoryAxisFormat(
       		                      cht.axisFormat().setLabel(getUsuario())))
		            .pageFooter(Templates.footerComponent)
		            .setDataSource(createDataSource())
		            .toPdf(os);	    	  
		
		      } catch (DRException e) {
		
		         e.printStackTrace();
		
		      }
		
		   }
	
	private String getUsuario() {
		if (tipoReporte.equals("3"))
			return "Proveedores";
		else if (tipoReporte.equals("6"))
			return "Usuarios";
		
		return null;
	}

	class DataReportReview {

		private String name;
		private Double reviewPromedio;
		private Integer cantidadServicios;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getReviewPromedio() {
			return reviewPromedio;
		}
		public void setReviewPromedio(Double reviewPromedio) {
			this.reviewPromedio = reviewPromedio;
		}
		public Integer getCantidadServicios() {
			return cantidadServicios;
		}
		public void setCantidadServicios(Integer cantidadServicios) {
			this.cantidadServicios = cantidadServicios;
		}
		
		
	}

	
}
