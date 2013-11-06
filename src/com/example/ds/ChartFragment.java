package com.example.ds;

import org.stockchart.StockChartActivity;
import org.stockchart.core.Area;
import org.stockchart.core.Axis.Side;
import org.stockchart.utils.StockDataGenerator;
import org.stockchart.utils.StockDataGenerator.Point;
import org.stockchart.series.BarSeries;
import org.stockchart.series.StockSeries;

import android.os.Bundle;

public class ChartFragment extends StockChartActivity
{
	private StockSeries fPriceSeries;
	private BarSeries fVolumeSeries;
	
	private static final int POINTS_COUNT = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		populateChart();
		
		this.getStockChartView().invalidate();
	}
	
	private void populateChart()
	{
		StockDataGenerator gen = new StockDataGenerator();
		for(int i=0;i<POINTS_COUNT;i++)
		{
			Point p = gen.getNextPoint();
		
			fPriceSeries.addPoint(p.o, p.h, p.l, p.c);
			fVolumeSeries.addPoint(0.0, p.v);
		}
	}
	
	@Override
	protected void initChart()
	{
		fPriceSeries = new StockSeries();
		fPriceSeries.setName("price");				
		fPriceSeries.setYAxisSide(Side.RIGHT);
		
		fVolumeSeries = new BarSeries();
		fVolumeSeries.setName("volume");
		fVolumeSeries.setYAxisSide(Side.LEFT);
		
		Area a = this.getStockChartView().addArea();
		
		a.getSeries().add(fVolumeSeries);
		a.getSeries().add(fPriceSeries);
	}

	@Override
	protected void restoreChart() 
	{
		fPriceSeries = (StockSeries) this.getStockChartView().findSeriesByName("price");
		fVolumeSeries = (BarSeries) this.getStockChartView().findSeriesByName("volume");
	}

}