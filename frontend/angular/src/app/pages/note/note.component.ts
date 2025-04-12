import { Component, signal } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { PopupComponent } from '../../components/popup/popup.component';
import { PopupConfiguration } from '../../models/popup/popup-conflig.type';

@Component({
  selector: 'app-note',
  imports: [HeaderComponent, PopupComponent],
  templateUrl: './note.component.html',
  styleUrl: './note.component.scss',
})
export class NoteComponent {
  createPopup = signal(false);

  getCreatePopup() {
    const config: PopupConfiguration = {
      title: 'Hello',
      handleClose: this.handleCloseCreate,
      ableBgClose: true,
      handleSubmit: function (): void | undefined {
        throw new Error('Function not implemented.');
      },
      description: 'hello',
      inputField: [
        {
          title: 'Date',
          type: 'date',
          placeholder: undefined,
        },
        {
          title: 'Date',
          type: 'text',
          placeholder: 'Gooooo',
        },
      ],
      button: undefined,
    };

    return config;
  }

  handleCloseCreate = (event: MouseEvent) => {
    this.createPopup.set(false);
  };

  handleOpenCreate = () => {
    this.createPopup.set(true);
  };
}
