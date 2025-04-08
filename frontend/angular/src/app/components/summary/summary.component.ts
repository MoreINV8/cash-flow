import { Component, inject, input, OnInit, signal } from '@angular/core';
import { SvgReaderService } from '../../services/svg-reader.service';
import { ColorSvgDirective } from '../../directives/color-svg.directive';
import { DashboardSummaryElement } from '../../models/dashboard/dashboard-summary-element.type';

@Component({
  selector: 'app-summary',
  imports: [ColorSvgDirective],
  templateUrl: './summary.component.html',
  styleUrl: './summary.component.scss',
})
export class SummaryComponent {
  summaryElement = input.required<DashboardSummaryElement>();

}
