package com.ssanjun.intellij.datagrip.jsonDataViewer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ssanjun on 2016. 7. 30..
 */
public class jsonDataViewerFactory implements ToolWindowFactory {

    private JPanel jsonDataViewerContent;
    private JTextArea jsonData;
    private JButton btnPrettyView;
    private JButton btnUglyView;
    private JScrollPane scrollPanel;
    private ToolWindow JsonDataViewerWindow;


    public jsonDataViewerFactory() {
        btnPrettyView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jsonDataViewerFactory.this.jsonPrettyView();
            }
        });
        btnUglyView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jsonDataViewerFactory.this.jsonUglyView();
            }
        });
    }

    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        JsonDataViewerWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jsonDataViewerContent, "", false);

        this.scrollPanel.setOpaque(false);
        this.scrollPanel.getViewport().setOpaque(false);
        this.scrollPanel.setBackground( new Color(255, 255, 255, 0) );

        toolWindow.getContentManager().addContent(content);
    }

    public void jsonPrettyView() {
        String jsonText = this.jsonData.getText();

        if (!jsonText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();

            try {
                JsonElement je = jp.parse(jsonText);
                String prettyJsonString = gson.toJson(je);
                this.jsonData.setText(prettyJsonString);

            } catch (Exception e) {
                Messages.showErrorDialog(
                        jsonDataViewerFactory.this.getClass().getName(), "Json Parse Error");
                e.printStackTrace();
            }
        }
    }


    public void jsonUglyView() {
        String jsonText = this.jsonData.getText();

        if (!jsonText.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            JsonParser jp = new JsonParser();

            try {
                JsonElement je = jp.parse(jsonText);
                String prettyJsonString = gson.toJson(je);
                this.jsonData.setText(prettyJsonString);

            } catch (Exception e) {
                Messages.showErrorDialog(
                        jsonDataViewerFactory.this.getClass().getName(), "Json Parse Error");
                e.printStackTrace();
            }
        }
    }


}
