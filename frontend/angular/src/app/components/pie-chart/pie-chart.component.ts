import {
  Component,
  inject,
  input,
  OnChanges,
  OnInit,
  signal,
  SimpleChanges,
} from '@angular/core';
import { DashboardCategory } from '../../models/dashboard/dashboard-category.type';
import { Chart } from 'chart.js';
import { HelperService } from '../../services/helper.service';

@Component({
  selector: 'app-pie-chart',
  imports: [],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.scss',
})
export class PieChartComponent implements OnInit, OnChanges {
  helper = inject(HelperService);

  categoryInputData = input.required<DashboardCategory[]>();

  categoryData = signal<DashboardCategory[]>([]);
  labelData = signal<string[]>([]);
  actualData = signal<number[]>([]);
  mainColorData = signal<string[]>([]);
  subColorData = signal<string[]>([]);
  itemCountData = signal<number[]>([]);
  percentageData = signal<number[]>([]);

  private chart: Chart | null = null;

  ngOnInit(): void {
    this.categoryData.set(this.categoryInputData());
    this.setData();

    this.generatePieChart();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['categoryInputData'] && !changes['categoryInputData'].firstChange) {
      this.clearData();

      this.categoryData.set(changes['categoryInputData'].currentValue);
      this.setData()

      this.generatePieChart();
    }
  }

  generatePieChart() {
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('pie-chart', {
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

  private clearData() {
    this.labelData.set([]);
    this.actualData.set([]);
    this.mainColorData.set([]);
    this.subColorData.set([]);
    this.itemCountData.set([]);
    this.percentageData.set([]);
  }

  private setData() {
    this.categoryData().map((value) => {
      this.actualData().push(value.total);
      this.mainColorData().push(value.color ?? '#eff6ff');
      this.subColorData().push(
        value.color != null
          ? this.helper.changeHexOpacity(value.color, 70)!
          : '#334155'
      );
      this.labelData().push(value.category);
      this.itemCountData().push(value.count);
      this.percentageData().push(value.percentage);
    });
  }
}
