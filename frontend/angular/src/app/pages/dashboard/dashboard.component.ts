import { Component, OnInit, signal } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { SelecterComponent } from '../../components/selecter/selecter.component';
import { MockDataService } from '../../services/mock-data.service';

@Component({
  selector: 'app-dashboard',
  imports: [HeaderComponent, SelecterComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  mockMonth = signal<string[]>([]);

  constructor (private mock:MockDataService) {}
  
  ngOnInit(): void {
    this.mockMonth.set(this.mock.mockMonth());
  }
  
}
