import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,
  inject,
  input,
  OnChanges,
  OnInit,
  signal,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { DashboardSpend } from '../../models/dashboard/dashboard-spend.type';
import { Chart } from 'chart.js/auto';
import { HelperService } from '../../services/helper.service';

@Component({
  selector: 'app-bar-line-chart',
  imports: [],
  templateUrl: './bar-line-chart.component.html',
  styleUrl: './bar-line-chart.component.scss',
})
export class BarLineChartComponent
  implements OnInit, OnChanges, AfterViewChecked
{
  @ViewChild('barLineChartCanvas', { static: true }) chartCanvas!: ElementRef;

  inputDashboardData = input.required<DashboardSpend[]>();

  private colorService = inject(HelperService);

  dashboardData = signal<DashboardSpend[]>([]);
  labelData = signal<string[]>([]);
  itemCountData = signal<number[]>([]);
  actualData = signal<string[]>([]);
  averageData = signal<string[]>([]);
  colorData = signal<string[]>([]);

  private chart: Chart<'bar' | 'line', string[], string> | null = null;

  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewChecked(): void {}

  ngOnInit(): void {
    if (this.dashboardData != null) {
      this.dashboardData.set(this.inputDashboardData());
      this.setUpData();

      this.generateBarLineChart();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (
      changes['inputDashboardData'] &&
      !changes['inputDashboardData'].firstChange
    ) {

      const changesData = changes['inputDashboardData'].currentValue;

      this.clearData();

      this.dashboardData.set(changesData);
      this.setUpData();

      this.generateBarLineChart();
      this.cdr.detectChanges();
    }
  }

  generateBarLineChart = () => {

    if (this.chart) {
      this.chart.destroy();
      this.chart = null; // Clear the reference
    }

    try {
      this.chart = new Chart(this.chartCanvas.nativeElement, {
        type: 'bar',
        data: {
          labels: this.labelData(),
          datasets: [
            {
              label: 'Average Spending',
              data: this.averageData(),
              backgroundColor: '#27272a',
              borderColor: '#27272a',
              hoverBackgroundColor: '#e2e8f0',
              type: 'line',
            },
            {
              label: 'Daily Spending',
              data: this.actualData(),
              maxBarThickness: 30,
              backgroundColor: '#fed7aa',
              hoverBorderColor: '#f97316',
              hoverBorderWidth: 2,
              borderRadius: 10,
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'bottom',
            },
            title: {
              display: true,
              text: `Daily Spend`,
            },
            tooltip: {
              callbacks: {
                label: (context) => {
                  const index = context.dataIndex;

                  return [
                    `Spend: ${this.actualData().at(index)}`,
                    `Records: ${this.itemCountData().at(index)}`,
                  ];
                },
              },
            },
          },
          scales: {
            y: {
              title: {
                display: true,
                text: 'Spends',
              },
            },
            x: {
              title: {
                display: true,
                text: 'Days',
              },
            },
          },
        },
      });
    } catch (err) {
      console.error(err);
    }
  };

  private setUpData() {
    let calmulative = 0;
    this.dashboardData().map((value, index) => {
      calmulative += value.spending;
      this.labelData().push(new Date(value.date).toLocaleDateString());
      this.actualData().push(value.spending.toFixed(2));
      this.averageData().push((calmulative / (index + 1)).toFixed(2));
      this.itemCountData().push(value.total_items);
    });
    this.colorData.set(
      this.colorService.colorGenerate(this.dashboardData().length)
    );
  }

  private clearData() {
    this.labelData.set([]);
    this.actualData.set([]);
    this.averageData.set([]);
    this.itemCountData.set([]);

    this.colorData.set([]);
  }
}
