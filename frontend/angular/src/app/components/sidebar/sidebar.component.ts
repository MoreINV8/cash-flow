import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ColorSvgDirective } from '../../directives/color-svg.directive';
import { Sidebar } from '../../models/sidbar.type';
import { ChangeTextColorDirective } from '../../directives/change-text-color.directive';
import { ChangeBgColorDirective } from '../../directives/change-bg-color.directive';
import { RewidthDirective } from '../../directives/rewidth.directive';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [ColorSvgDirective, ChangeTextColorDirective, ChangeBgColorDirective, RewidthDirective, RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit {
  inputIsOpen = input(false);
  closeSidebar = input.required < (event: MouseEvent) => void> ();
  at = input<number>();
  
  sidebarItems = signal<Sidebar[]>([]);
  
  ngOnInit(): void {
    this.sidebarItems().push({
      lable: 'Dashbord',
      path: '/',
      iconPath: '/icons/layout-fluid.svg',
    });
    
    this.sidebarItems().push({
      lable: 'Categories',
      path: '/',
      iconPath: '/icons/tags.svg',
    });
    
    this.sidebarItems().push({
      lable: 'Note',
      path: '/note',
      iconPath: '/icons/coins.svg',
    });
  }

}
