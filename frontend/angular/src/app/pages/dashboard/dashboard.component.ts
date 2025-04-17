import { ChangeDetectorRef, Component, inject, OnInit, signal } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { SelecterComponent } from '../../components/selecter/selecter.component';
import { BackendConnectService } from '../../services/backend-connect.service';
import { User } from '../../models/user.type';
import { Dashboard } from '../../models/dashboard/dashboard.type';
import { lastValueFrom } from 'rxjs';
import { SummaryComponent } from '../../components/summary/summary.component';
import { DashboardSummaryElement } from '../../models/dashboard/dashboard-summary-element.type';
import { BarLineChartComponent } from '../../components/bar-line-chart/bar-line-chart.component';
import { CommonModule } from '@angular/common';
import { PieChartComponent } from '../../components/pie-chart/pie-chart.component';
import { TopSpendComponent } from '../../components/top-spend/top-spend.component';
import { Month } from '../../models/month.type';
import { DashboardWithMonth } from '../../models/dashboard-with-month.type';

@Component({
  selector: 'app-dashboard',
  imports: [
    HeaderComponent,
    SelecterComponent,
    SummaryComponent,
    BarLineChartComponent,
    CommonModule,
    PieChartComponent,
    TopSpendComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  private backend = inject(BackendConnectService);
  private changeDetectorRef = inject(ChangeDetectorRef);
  
  private user: User | null = null;
  
  months = signal<Month[]>([]);
  selectedMonth = signal(0);
  data = signal<Dashboard | null>(null);
  summaryDecorate = signal<Map<string, DashboardSummaryElement>>(new Map());
  mainData = signal<DashboardWithMonth[]>([]);
  
  async ngOnInit(): Promise<void> {
    this.summaryDecorate().set('total-spend', {
      iconPath: '/icons/dollar.svg',
      iconBackground: '#dbeafe',
      iconColor: '#2563eb',
      label: 'Total Spending',
      value: 0,
    });
    this.summaryDecorate().set('average', {
      iconPath: '/icons/chart-histogram.svg',
      iconBackground: '#dcfce7',
      iconColor: '#16a34a',
      label: 'Average Daily Spending',
      value: 0,
    });
    this.summaryDecorate().set('highest', {
      iconPath: '/icons/exclamation.svg',
      iconBackground: '#fef9c3',
      iconColor: '#ca8a04',
      label: 'Highest Expense',
      value: 0,
    });

    try {
      const user = await lastValueFrom(
        this.backend.postLogin({ username: 'time', password: 't123' })
      );
      this.user = user;
      console.log(user);
      
      if (this.user == null) {
        console.log('User is null');
        return;
      }
      
      const data = await lastValueFrom(
        this.backend.getDashboard(user)
      );

      this.mainData.set(data);

      if (data.length === 0) { return; } // handle no month ex. pop up to create? plank page?

      this.data.set(this.mainData().at(this.selectedMonth())?.month_dashboard ?? null);
      console.log(data);

      if (this.data() == null) {
        console.log('Data is null');
        return;
      }

      this.mainData().map(value => {
        this.months().push(value.noted_month);
      })

      const summary = this.data()?.summary;
      this.setKeyValue(summary?.total_spending, summary?.average_daily_spending, this.data()?.top_spend_records.at(0)?.amount);

      this.changeDetectorRef.detectChanges();

    } catch (error) {
      console.error('Error:', error);
    }
  }

  private setKeyValue(spend:number = 0, average:number=0, highest:number=0) {
    const totalSpendElement = this.summaryDecorate().get('total-spend');
    if (totalSpendElement) {
      totalSpendElement.value = spend;
    }
    const averageElement = this.summaryDecorate().get('average');
    if (averageElement) {
      averageElement.value = average;
    }
    const highestElement = this.summaryDecorate().get('highest');
    if (highestElement) {
      highestElement.value = highest;
    }
  }

  handleChangeItem = async (mId: string, selected: number) => {
    try {
      // set null to notify angular to get change value
      this.data.set(null)

      this.selectedMonth.set(selected);
      this.data.set(this.mainData().at(this.selectedMonth())!.month_dashboard);

      const summary = this.data()?.summary;
      this.setKeyValue(
        summary?.total_spending,
        summary?.average_daily_spending,
        this.data()?.top_spend_records.at(0)?.amount
      );

      this.changeDetectorRef.detectChanges();

    } catch (error) {
      console.error('Error:', error);
    }
  }

}
