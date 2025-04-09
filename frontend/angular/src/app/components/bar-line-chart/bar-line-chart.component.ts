import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DashboardSpend } from '../../models/dashboard/dashboard-spend.type';
import { Chart } from 'chart.js/auto';
import { HelperService } from '../../services/helper.service';
import { Color } from '../../models/color.type';

@Component({
  selector: 'app-bar-line-chart',
  imports: [],
  templateUrl: './bar-line-chart.component.html',
  styleUrl: './bar-line-chart.component.scss',
})
export class BarLineChartComponent implements OnInit {
  dashboardData = input.required<DashboardSpend[]>();

  private colorService = inject(HelperService);

  labelData = signal<string[]>([]);
  itemCountData = signal<number[]>([]);
  actualData = signal<string[]>([]);
  averageData = signal<string[]>([]);
  colorData = signal<string[]>([]);

  ngOnInit(): void {
    if (this.dashboardData != null) {
      this.dashboardData().map((value, index) => {
        this.labelData().push((new Date(value.date)).toLocaleDateString());
        this.actualData().push(value.spending.toFixed(2));
        this.averageData().push((value.spending / (index + 1)).toFixed(2));
        this.itemCountData().push(value.total_items);
      });
      this.colorData.set(this.colorService.colorGenerate(this.dashboardData().length));

      this.generateBarLineChart();
    }
  }

  generateBarLineChart = () => {
    const chart = new Chart('bar-line-chart', {
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
              }
            }
          }
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
  };
}
