package com.jschool.configuration;


import com.jschool.beans.UpdatePageBean;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ChartJsView {

    @Inject
    private UpdatePageBean updatePageBean;

    private DonutChartModel donutModel;

    @PostConstruct
    public void init() {
        createDonutModel();
    }
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(updatePageBean.getProductList().get(0).getResultAmount());
        values.add(updatePageBean.getProductList().get(1).getResultAmount());
        values.add(updatePageBean.getProductList().get(2).getResultAmount());
        values.add(updatePageBean.getProductList().get(3).getResultAmount());
        values.add(updatePageBean.getProductList().get(4).getResultAmount());
        values.add(updatePageBean.getProductList().get(5).getResultAmount());
        values.add(updatePageBean.getProductList().get(6).getResultAmount());
        values.add(updatePageBean.getProductList().get(7).getResultAmount());
        values.add(updatePageBean.getProductList().get(8).getResultAmount());
        values.add(updatePageBean.getProductList().get(9).getResultAmount());
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 38, 38)");//red
        bgColors.add("rgb(255, 98, 224)");//pink
        bgColors.add("rgb(255, 169, 18)");//orange
        bgColors.add("rgb(255, 255, 57)");//yellow
        bgColors.add("rgb(64, 255, 34)");//green
        bgColors.add("rgb(98, 255, 209)");//turquoise
        bgColors.add("rgb(36, 190, 255)");//light blue
        bgColors.add("rgb(36, 102, 255)");//blue
        bgColors.add("rgb(182, 33, 255)");//violett
        bgColors.add("rgb(176, 176, 176)");//grey

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("№1");
        labels.add("№2");
        labels.add("№3");
        labels.add("№4");
        labels.add("№5");
        labels.add("№6");
        labels.add("№7");
        labels.add("№8");
        labels.add("№9");
        labels.add("№10");
        data.setLabels(labels);

        donutModel.setData(data);
    }
    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }
}
