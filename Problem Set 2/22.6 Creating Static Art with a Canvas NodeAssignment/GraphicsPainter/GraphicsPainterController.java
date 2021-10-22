package GraphicsPainter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicsPainterController implements Initializable {
    public ToggleGroup shapes;
    double startX, startY, lastX, lastY, oldX, oldY;
    private GraphicsContext gcB,gcF;
    private boolean drawLine = false, drawOval = false, drawRectangle = false, freeDesign = true;

    @FXML private ToggleGroup shapeToggleGroup;
    @FXML private Slider sizeSlider;
    @FXML private ColorPicker colorPick;
    @FXML private RadioButton strokeRB, fillRB;
    @FXML private Canvas theCanvas, canvasGo;
    @FXML private RadioButton rectButton, lineButton, ovalButton, pencilButton;
    @FXML private Button clearButton;

    @FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        this.oldX = e.getX();
        this.oldY = e.getY();
    }

    @FXML
    private void onMouseDraggedListener(MouseEvent e){
        this.lastX = e.getX();
        this.lastY = e.getY();

        if(drawRectangle)
            drawRectEffect();
        if(drawOval)
            drawOvalEffect();
        if(drawLine)
            drawLineEffect();
        if(freeDesign)
            freeDrawing();
    }

    @FXML
    private void onMouseReleaseListener(MouseEvent e){
        if(drawRectangle)
            drawRect();
        if(drawOval)
            drawOval();
        if(drawLine)
            drawLine();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Draw methods<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void drawRect()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        gcB.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            gcB.setFill(colorPick.getValue());
            gcB.fillRect(startX, startY, wh, hg);
        }else{
            gcB.setStroke(colorPick.getValue());
            gcB.strokeRect(startX, startY, wh, hg);
        }
    }

    private void drawOval()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        gcB.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            gcB.setFill(colorPick.getValue());
            gcB.fillOval(startX, startY, wh, hg);
        }else{
            gcB.setStroke(colorPick.getValue());
            gcB.strokeOval(startX, startY, wh, hg);
        }
    }

    private void drawLine()
    {
        gcB.setLineWidth(sizeSlider.getValue());
        gcB.setStroke(colorPick.getValue());
        gcB.strokeLine(startX, startY, lastX, lastY);
    }

    private void freeDrawing()
    {
        gcB.setLineWidth(sizeSlider.getValue());
        gcB.setStroke(colorPick.getValue());
        gcB.strokeLine(oldX, oldY, lastX, lastY);
        oldX = lastX;
        oldY = lastY;
    }

    //////////////////////////////////////////////////////////////////////
    //>>>>>>>>>>>>>>>>>>>>>>>>>>Draw effects methods<<<<<<<<<<<<<<<<<<<<<<<

    private void drawRectEffect()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        gcF.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            gcF.clearRect(0, 0, canvasGo.getWidth(), canvasGo.getHeight());
            gcF.setFill(colorPick.getValue());
            gcF.fillRect(startX, startY, wh, hg);
        }else{
            gcF.clearRect(0, 0, canvasGo.getWidth(), canvasGo.getHeight());
            gcF.setStroke(colorPick.getValue());
            gcF.strokeRect(startX, startY, wh, hg );
        }
    }

    private void drawOvalEffect()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        gcF.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            gcF.clearRect(0, 0, canvasGo.getWidth(), canvasGo.getHeight());
            gcF.setFill(colorPick.getValue());
            gcF.fillOval(startX, startY, wh, hg);
        }else{
            gcF.clearRect(0, 0, canvasGo.getWidth(), canvasGo.getHeight());
            gcF.setStroke(colorPick.getValue());
            gcF.strokeOval(startX, startY, wh, hg );
        }
    }

    private void drawLineEffect()
    {
        gcF.setLineWidth(sizeSlider.getValue());
        gcF.setStroke(colorPick.getValue());
        gcF.clearRect(0, 0, canvasGo.getWidth() , canvasGo.getHeight());
        gcF.strokeLine(startX, startY, lastX, lastY);
    }

    ///////////////////////////////////////////////////////////////////////
    //>>>>>>>>>>>>>>>>>>>>>>>>>>Clear Canvas<<<<<<<<<<<<<<<<<<<<<<<

    @FXML
    private void clearCanvas(ActionEvent e)
    {
        gcB.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
        gcF.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
    }

    //>>>>>>>>>>>>>>>>>>>>>Buttons control<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @FXML
    private void setOvalAsCurrentShape(ActionEvent e)
    {
        drawLine = false;
        drawOval = true;
        drawRectangle = false;
        freeDesign = false;
    }

    @FXML
    private void setLineAsCurrentShape(ActionEvent e)
    {
        drawLine = true;
        drawOval = false;
        drawRectangle = false;
        freeDesign = false;
    }
    @FXML
    private void setRectangleAsCurrentShape(ActionEvent e)
    {
        drawLine = false;
        drawOval = false;
        freeDesign = false;
        drawRectangle = true;
    }

    @FXML
    private void setFreeDesign(ActionEvent e)
    {
        drawLine = false;
        drawOval = false;
        drawRectangle = false;
        freeDesign = true;
    }

    //////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gcB = theCanvas.getGraphicsContext2D();
        gcF = canvasGo.getGraphicsContext2D();

        sizeSlider.setMin(1);
        sizeSlider.setMax(7);
    }
}