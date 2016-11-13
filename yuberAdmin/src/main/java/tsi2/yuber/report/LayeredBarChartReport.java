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

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import tsi.tuber.admin.enums.TipoVerticalEnum;


public class LayeredBarChartReport extends AbstractReport {

	   private JRDataSource createDataSource() {
			DRDataSource dataSource = new DRDataSource("user", "gasto", "cantServ");

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(getUrl());
			
			try {
				HttpResponse response = client.execute(request);
				
				TypeToken<List<DataReportServicio>> token = new TypeToken<List<DataReportServicio>>(){};
				List<DataReportServicio> dataList = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), token.getType());

				for (DataReportServicio item : dataList) {
					dataSource.add(item.name, item.gastos, item.cantidadServicios);
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

		private String getUsuario() {
			if (tipoReporte.equals("4"))
				return "Proveedor";
			else if (tipoReporte.equals("5"))
				return "Usuario";		

		return null;
	}

		public LayeredBarChartReport(OutputStream os, String vertical, TipoVerticalEnum tve, String tipoReporte) {
			 super(vertical,tve,tipoReporte);
		     build(os);
	   }
		
		private void build(OutputStream os) {
			
			      FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
			      TextColumnBuilder<String> userColumn = col.column(getUsuario(), "user", type.stringType());
			      TextColumnBuilder<Double> costoColumn = col.column(getGasto(), "gasto", type.doubleType());
			      TextColumnBuilder<Integer> cantServColumn = col.column("Cantidad Servicios", "cantServ", type.integerType());
			
			      try {
			    	      	  
			    	  JasperReportBuilder jrb =  DynamicReports.report()
			            .setTemplate(Templates.reportTemplate)
			            .columns(userColumn, costoColumn, cantServColumn)
			            .title(Templates.createTitleComponent("Reporte de " + getReporte()));
			    	  
			    	    jrb.summary(
			                 cht.layeredBarChart()
			                  .setTitle("Reporte de " + getReporte())
			                  .setTitleFont(boldFont)
			                  .seriesBarWidths(1.0, 0.5)
                              .setCategory(userColumn)
	                          .series(
	                               cht.serie(costoColumn), cht.serie(cantServColumn))
	                          .setCategoryAxisFormat(
	                               cht.axisFormat().setLabel(getUsuario())));
			    	  
			    			  
			    	  jrb.pageFooter(Templates.footerComponent)
			            .setDataSource(createDataSource())
			            .toPdf(os);	    	  
			
			      } catch (DRException e) {
			
			         e.printStackTrace();
			
			      }
			
			   }
		
		private String getReporte() {
			if (tipoReporte.equals("4"))
				return "Ganancia por Proveedor";
			else if (tipoReporte.equals("5"))
				return "Servicios por Usuario";		
			return null;
		}

		private String getGasto() {
			if (tipoReporte.equals("4"))
				return "Ganancia";
			else if (tipoReporte.equals("5"))
				return "Gasto";
			return null;
		}

		class DataReportServicio {

			private String name;
			private Double gastos;
			private Integer cantidadServicios;

			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public Double getGastos() {
				return gastos;
			}
			public void setGastos(Double gastos) {
				this.gastos = gastos;
			}
			public Integer getCantidadServicios() {
				return cantidadServicios;
			}
			public void setCantidadServicios(Integer cantidadServicios) {
				this.cantidadServicios = cantidadServicios;
			}
			
		}

	
	
}
