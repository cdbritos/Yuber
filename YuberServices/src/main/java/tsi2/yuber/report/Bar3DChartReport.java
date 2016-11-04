package tsi2.yuber.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.OutputStream;
import java.math.BigDecimal;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class Bar3DChartReport {

   private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
		dataSource.add("Tablet", 350, new BigDecimal(300));
		dataSource.add("Laptop", 300, new BigDecimal(500));
		dataSource.add("Smartphone", 450, new BigDecimal(250));
		return dataSource;
   }

	public Bar3DChartReport(OutputStream os) {
	      build(os);
   }
	
	private void build(OutputStream os) {
		
		      FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		      TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType());
		      TextColumnBuilder<Integer> quantityColumn = col.column("Quantity", "quantity", type.integerType());
		      TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("Unit price", "unitprice", type.bigDecimalType());
		
		      try {
		    	      	  
		    	  DynamicReports.report()
		            .setTemplate(Templates.reportTemplate)
		            .columns(itemColumn, quantityColumn, unitPriceColumn)
		            .title(Templates.createTitleComponent("Bar3DChart"))
		            .summary(
		                 cht.bar3DChart()
		                  .setTitle("Bar 3D chart")
		                  .setTitleFont(boldFont)
		                 .setCategory(itemColumn)
		                  .series(
		                     cht.serie(quantityColumn), cht.serie(unitPriceColumn))
		                  .setCategoryAxisFormat(
		                     cht.axisFormat().setLabel("Item")))
		            .pageFooter(Templates.footerComponent)
		            .setDataSource(createDataSource())
		            .toPdf(os);	    	  
		
		      } catch (DRException e) {
		
		         e.printStackTrace();
		
		      }
		
		   }
	
}
