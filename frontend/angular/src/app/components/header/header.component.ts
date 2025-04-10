import { Component, input, signal } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'app-header',
  imports: [SidebarComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  title = input.required<string>()
  at = input.required<number>();

  isSidebarOpen = signal(false);

  handleOpenSidebar = () => {
    this.isSidebarOpen.set(true);
  }

  handleCloseSidebar = (event: MouseEvent) => {
    const clickedElement = event.target as HTMLElement;

    if (clickedElement.classList.contains('sidebar-bg')) {
      this.isSidebarOpen.set(false);
    }
  }
}
