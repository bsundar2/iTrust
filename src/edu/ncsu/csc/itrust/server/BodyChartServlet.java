package edu.ncsu.csc.itrust.server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.ShapeUtilities;

import edu.ncsu.csc.itrust.action.BodyMeasurementAction;
import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;

/**
 * 
 * @author Jay Patel
 * 
 * Servlet for the body measurement graphs. The user will choose which graph they want to see on the JSP this will pass
 * parameters to this servlet. When the servlet gets the request it will generate a PNG of the graph and send it back to
 * the JSP page to be embedded. Chart creation is dynamic and depends on input parameters.
 *
 */
public class BodyChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public BodyChartServlet() {

	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OutputStream out = response.getOutputStream(); /* Get the output stream from the response object */
        String mid = request.getParameter("mid");
        String type = request.getParameter("type");
        try {
        	
        	DAOFactory factory = DAOFactory.getProductionInstance();
        	final TimeSeries series = new TimeSeries( type );
        	BodyMeasurementAction action = new BodyMeasurementAction(factory, Long.parseLong(mid, 10));
        	List<BodyMeasurementBean> bmlist = action.getBodyMeasurementWithMIDASC();
        	
        	if(type.equals("Weight")){ //HANDLES Weight Chart Creation
	        	for (BodyMeasurementBean bmbean: bmlist) {

	    			//needed to parse to get the date in the correct format
	    			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
	    			sdf.setLenient(false); //make a no exception rule for the format
	    			Date parsedDate = sdf.parse(bmbean.getLogDate()); //parse with format in mind
	
	        		series.add(new Day(parsedDate), bmbean.getWeight());
	        	}
        	} else if(type.equals("Height")){ //HANDLES Height Chart Creation
	        	for (BodyMeasurementBean bmbean: bmlist) {
	        		
	    			//needed to parse to get the date in the correct format
	    			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
	    			sdf.setLenient(false); //make a no exception rule for the format
	    			Date parsedDate = sdf.parse(bmbean.getLogDate()); //parse with format in mind
	
	        		series.add(new Day(parsedDate), bmbean.getHeight());
	        	}
        	} else if(type.equals("Waist")){
	        	for (BodyMeasurementBean bmbean: bmlist) {
	        		
	    			//needed to parse to get the date in the correct format
	    			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
	    			sdf.setLenient(false); //make a no exception rule for the format
	    			Date parsedDate = sdf.parse(bmbean.getLogDate()); //parse with format in mind
	
	        		series.add(new Day(parsedDate), bmbean.getWaist());
	        	}
        	} else if(type.equals("Arms")){
	        	for (BodyMeasurementBean bmbean: bmlist) {
	        		
	    			//needed to parse to get the date in the correct format
	    			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
	    			sdf.setLenient(false); //make a no exception rule for the format
	    			Date parsedDate = sdf.parse(bmbean.getLogDate()); //parse with format in mind
	
	        		series.add(new Day(parsedDate), bmbean.getArms());
	        	}
        	}
        	
        	//Unit decider
        	String unit = "";
        	if(type.equals("Weight")){
        		unit = " (lbs.)";
        	}else{
        		unit = " (in.)";
        	}
        	
        	//Title Decider
        	String cTitle = "Patient " + type + " History"; 
        	
        	
        	final XYDataset dataset=( XYDataset )new TimeSeriesCollection(series);
            JFreeChart timechart = ChartFactory.createTimeSeriesChart(
                    cTitle, 
                    "Date", 
                    type + unit, 
                    dataset,
                    false, 
                    false, 
                    false);
            

            
            XYPlot plot = (XYPlot) timechart.getPlot();
            XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
            r.setSeriesShape(0, ShapeUtilities.createDiamond(5));
            r.setSeriesShapesVisible(0, true);
            
            Color c = new Color(249, 249, 249);
            plot.setBackgroundPaint(c);
            
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setRangeGridlinePaint(Color.BLACK);
            
            plot.getDomainAxis().setVerticalTickLabels(true);

            response.setContentType("image/png"); /* Set the HTTP Response Type */
            ChartUtilities.writeChartAsPNG(out, timechart, 800, 600); /* Write the data to the output stream */
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
        finally {
            out.close(); /* Close the output stream */
        }
	}
}