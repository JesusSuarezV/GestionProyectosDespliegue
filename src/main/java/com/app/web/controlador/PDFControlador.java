package com.app.web.controlador;

import com.app.web.entidad.Proyecto;
import com.app.web.servicio.APUItemSubproyectoServicio;
import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.ManoObraAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.servicio.TransporteServicio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.hibernate.service.spi.ServiceBinding.ServiceLifecycleOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PDFControlador {
	
	@Autowired
	private ProyectoServicio servicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private TransporteServicio transporteServicio;
	@Autowired
	private MaquinariaAPUItemSubproyectoServicio maquinariaAPUItemSubproyectoServicio;
	@Autowired
	private ManoObraAPUItemSubproyectoServicio manoObraAPUItemSubproyectoServicio;

    @GetMapping("/Proyectos/{id}/Visualizar/Generar_PDF")
    public void generatePdf(@PathVariable int id, HttpServletResponse response) throws DocumentException, IOException {
        
    	Proyecto proyecto = servicio.obtenerProyectoPorId(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + proyecto.getNombre() +".pdf");
        
        
        double item = 0;
        double subItem = 0;
        double apu = 0;
        double apuSum = 0.1;
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        Font verdanaFont = getVerdanaFont(7, Font.NORMAL);
        Font verdanaBFont = getVerdanaFont(7, Font.BOLD);
        document.open();

        
        PdfPTable table = new PdfPTable(1); 

       
        table.addCell(getCell(proyecto.getNombre(), 1, verdanaBFont, Element.ALIGN_CENTER));

        document.add(table);
        
        table = new PdfPTable(1);
        
        table.addCell(getCell("PRESUPUESTO DE OBRA", 1, verdanaFont, Element.ALIGN_CENTER));
        document.add(table);
        
        table = new PdfPTable(7);
        table.addCell(getCell("ITEMS", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("DESCRIPCIÓN ACTIVIDADES", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("UND", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("CANT", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. UNIT", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. PARCIAL", 1, verdanaFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. CAPITULO", 1, verdanaFont, Element.ALIGN_CENTER));
        document.add(table);
        
        List<Subproyecto> subproyectos = subproyectoServicio.listarTodosLosSubproyectosDeUnProyecto(proyecto);
        for (Subproyecto subproyecto : subproyectos) {
        	item = 0;
        	table = new PdfPTable(1);
        	table.addCell(getCell(subproyecto.getNombre(), 1, verdanaFont, Element.ALIGN_CENTER));
        	document.add(table);
        	List<ItemSubproyecto> itemSubproyectos = itemSubproyectoServicio.listarTodosLosItemsDeUnSubproyecto(subproyecto);
        	for(ItemSubproyecto itemSubproyecto : itemSubproyectos) {
        		item++;
        		apu = 0;
        		table = new PdfPTable(7);
        		table.addCell(getCell(item + "", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell(itemSubproyecto.getItem().getNombre(), 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell(itemSubproyectoServicio.obtenerValorDeUnItemSubproyecto(itemSubproyecto)+"", 1, verdanaFont, Element.ALIGN_CENTER));
            	document.add(table);
            	List<APUItemSubproyecto> apuItemSubproyectos = apuItemSubproyectoServicio.listarTodosLosAPUDeUnItemDeUnSubproyecto(itemSubproyecto);
            	for(APUItemSubproyecto apuItemSubproyecto : apuItemSubproyectos) {
            		apu += apuSum;
            		subItem = item;
            		if (apu == 1) {
						apu = 0.10;
						apuSum = apuSum/10;
					}
            		subItem += apu;
            		
            		
            		table = new PdfPTable(7);
            		table.addCell(getCell(subItem + "", 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyecto.getApu().getNombre(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyecto.getApu().getUnidad(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyecto.getCantidad() + "", 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyectoServicio.obtenerValorUnitarioDeUnAPUItemSubproyecto(apuItemSubproyecto) + "", 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyectoServicio.obtenerValorDeUnAPUItemSubproyecto(apuItemSubproyecto)+ "", 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
                	document.add(table);
            	}
            	
        	}
        	
        	table = new PdfPTable(7);
        	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
        	table.addCell(getCell("SUBTOTAL " + subproyecto.getNombre(), 5, verdanaBFont, Element.ALIGN_LEFT));
        	table.addCell(getCell(subproyectoServicio.obtenerValorDeUnSubproyecto(subproyecto) + "", 1, verdanaFont, Element.ALIGN_CENTER));
        	document.add(table);
        }
        
        table = new PdfPTable(7);
    	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
    	table.addCell(getCell("COSTO TOTAL DEL PROYECTO", 5, verdanaBFont, Element.ALIGN_LEFT));
    	table.addCell(getCell(servicio.obtenerValorDeUnProyecto(proyecto) + "", 1, verdanaFont, Element.ALIGN_CENTER));
    	document.add(table);
        
        document.close();
    }
    
    private static PdfPCell getCell(String text, int colspan, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static Font getVerdanaFont(float size, int style) {
        return FontFactory.getFont("Verdana", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size, style);
    }
}
