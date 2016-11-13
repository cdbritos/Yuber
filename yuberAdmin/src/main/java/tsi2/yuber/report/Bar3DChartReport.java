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
import tsi.tuber.admin.enums.TipoVerticalEnum;

public class Bar3DChartReport extends AbstractReport {

   private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("vertical", "usuarios", "proveedores");

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(getUrl());
		
		try {
			HttpResponse response = client.execute(request);
			
			TypeToken<List<DataReportUsuarioProveedor>> token = new TypeToken<List<DataReportUsuarioProveedor>>(){};
			List<DataReportUsuarioProveedor> dataList = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), token.getType());

			for (DataReportUsuarioProveedor item : dataList) {
				dataSource.add(item.verticalName, item.cantidadUsuarios, item.cantidadProveedores);
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

	public Bar3DChartReport(OutputStream os, TipoVerticalEnum tve, String tipoReporte) {
		 super(tve,tipoReporte);
	     build(os);
   }
	
	private void build(OutputStream os) {
		
		      FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		      TextColumnBuilder<String> verticalColumn = col.column("Vertical", "vertical", type.stringType());
		      TextColumnBuilder<Integer> usuariosColumn = col.column("Usuarios", "usuarios", type.integerType());
		      TextColumnBuilder<Integer> proveedorColumn = col.column("Proveedores", "proveedores", type.integerType());
		
		      try {
		    	      	  
		    	  DynamicReports.report()
		            .setTemplate(Templates.reportTemplate)
		            .columns(verticalColumn, usuariosColumn, proveedorColumn)
		            .title(Templates.createTitleComponent("Reporte de Usuarios y Proveedores"))
		            .summary(
		                 cht.bar3DChart()
		                  .setTitle("Reporte de Usuarios y Proveedores")
		                  .setTitleFont(boldFont)
		                 .setCategory(verticalColumn)
		                  .series(
		                     cht.serie(usuariosColumn), cht.serie(proveedorColumn))
		                  .setCategoryAxisFormat(
		                     cht.axisFormat().setLabel("Vertical")))
		            .pageFooter(Templates.footerComponent)
		            .setDataSource(createDataSource())
		            .toPdf(os);	    	  
		
		      } catch (DRException e) {
		
		         e.printStackTrace();
		
		      }
		
		   }
	
	class DataReportUsuarioProveedor {

		private String verticalName;
		private Integer cantidadUsuarios;
		private Integer cantidadProveedores;
		
		public String getVerticalName() {
			return verticalName;
		}
		public void setVerticalName(String verticalName) {
			this.verticalName = verticalName;
		}
		public Integer getCantidadUsuarios() {
			return cantidadUsuarios;
		}
		public void setCantidadUsuarios(Integer cantidadUsuarios) {
			this.cantidadUsuarios = cantidadUsuarios;
		}
		public Integer getCantidadProveedores() {
			return cantidadProveedores;
		}
		public void setCantidadProveedores(Integer cantidadProveedores) {
			this.cantidadProveedores = cantidadProveedores;
		}
		
	}

	
}
