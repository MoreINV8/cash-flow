import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DashboardCategory } from '../../models/dashboard/dashboard-category.type';
import { Chart } from 'chart.js';
import { HelperService } from '../../services/helper.service';

@Component({
  selector: 'app-pie-chart',
  imports: [],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.scss',
})
export class PieChartComponent implements OnInit {
  helper = inject(HelperService);

  categoryData = input.required<DashboardCategory[]>();

  labelData = signal<string[]>([]);
  actualData = signal<number[]>([]);
  mainColorData = signal<string[]>([]);
  subColorData = signal<string[]>([]);
  itemCountData = signal<number[]>([]);
  percentageData = signal<number[]>([]);

  ngOnInit(): void {
    this.categoryData().map((value) => {
      this.actualData().push(value.total);
      this.mainColorData().push(value.color ?? '#eff6ff');
      this.subColorData().push(
        value.color != null ? this.helper.changeHexOpacity(value.color, 70)! : '#334155'
      );
      this.labelData().push(value.category);
      this.itemCountData().push(value.count);
      this.percentageData().push(value.percentage);
    });

    this.generatePieChart();
  }

  generatePieChart() {
    const chart = new Chart('pie-chart', {
      type: 'doughnut',
      data: {
        labels: this.labelData(),
        datasets: [
          {
            label: 'category',
            data: this.actualData(),
            backgroundColor: this.mainColorData(),
            borderRadius: 10,
            // circumference: 90,
            hoverBackgroundColor: this.subColorData(),
            hoverBorderColor: this.mainColorData(),
            hoverBorderWidth: 2,
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
            text: 'Categorize Spend',
          },
          tooltip: {
            callbacks: {
              label: (context) => {
                const index = context.dataIndex;

                return [
                  `Spend: ${this.actualData().at(index)?.toFixed(2)}฿`,
                  `Items: ${this.itemCountData().at(index)}`,
                  `Percent: ${this.percentageData().at(index)?.toFixed(2)}%`,
                ];
              },
            },
          },
        },
      },
    });
  }
}
