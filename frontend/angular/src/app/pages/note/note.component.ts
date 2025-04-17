import { Component, inject, OnInit, signal } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
import { HeaderComponent } from '../../components/header/header.component';
import { PopupComponent } from '../../components/popup/popup.component';
import { PopupConfiguration } from '../../models/popup/popup-conflig.type';
import { lastValueFrom } from 'rxjs';
import { BackendConnectService } from '../../services/backend-connect.service';
import { Category } from '../../models/category.type';
import { Month } from '../../models/month.type';
import { Day } from '../../models/day.type';
import { HelperService } from '../../services/helper.service';
import { ColorSvgDirective } from '../../directives/color-svg.directive';
import { CommonModule } from '@angular/common';
import { NoteTableComponent } from '../../components/note-table/note-table.component';

@Component({
  selector: 'app-note',
  imports: [
    HeaderComponent,
    PopupComponent,
    ColorSvgDirective,
    CommonModule,
    NoteTableComponent,
  ],
  templateUrl: './note.component.html',
  styleUrl: './note.component.scss',
})
export class NoteComponent implements OnInit {
  atMonth = signal(0);
  createPopup = signal(false);
  updatePopup = signal(false);

  updateDay = signal<undefined | Day>(undefined);

  noteData = signal<{ month: Month; days: Day[] }[] | undefined>(undefined);
  categories = signal<Category[] | undefined>(undefined);

  private backend = inject(BackendConnectService);
  private helper = inject(HelperService);

  async ngOnInit(): Promise<void> {
    const user = await lastValueFrom(
      this.backend.postLogin({
        username: 'time',
        password: 't123',
      })
    );

    const data = await lastValueFrom(this.backend.getNote(user));
    this.noteData.set(data.latest_2_months);
    this.categories.set(data.categories);
  }

  getMonth() {
    return this.helper.convertMonth(this.noteData()?.at(this.atMonth())?.month);
  }

  getUpadatePopup(day: Day): PopupConfiguration {
    if (this.updateDay()) {
      return {
        title: 'Edit Record',
        ableBgClose: true,
        handleClose: this.handleCloseUpdate,
        handleSubmit: (control) => {
          return null;
        },
        description: undefined,
        inputField: [
          {
            title: 'Date',
            type: 'date',
            placeholder: undefined,
            require: true,
            validator: (control) => {
              const i = new Date(control.value);
              if (i) {
                const today = Date.now();

                const noteMonth = this.noteData()?.at(this.atMonth())?.month;

                if (
                  i.getMonth() + 1 === noteMonth?.month &&
                  i.getTime() < today
                )
                  return null;
                else return { invalidDate: true } as ValidationErrors;
              }

              return null;
            },
            optionItems: undefined,
            default: day.date,
          },
          {
            type: 'option',
            title: 'Category',
            placeholder: undefined,
            require: false,
            optionItems: this.categories(),
            validator: undefined,
            default: this.categories()?.find((value) => {
              return (
                value.c_name === day.category_name &&
                value.color === day.category_color
              );
            })?.c_id,
          },
          {
            type: 'number',
            title: 'Amount',
            placeholder: undefined,
            require: true,
            optionItems: undefined,
            validator: (control) => {
              const i = control.value;

              if (i < 0) {
                return { invalidValue: true } as ValidationErrors;
              }
              return null;
            },
            default: day.transaction_value,
          },
          {
            title: 'Label',
            type: 'text',
            placeholder: 'Gooooo',
            require: false,
            validator: undefined,
            optionItems: undefined,
            default: day.note,
          },
        ],
        button: undefined,
      };
    } else {
      return {
        title: '',
        ableBgClose: false,
        handleClose: function (): void {
          throw new Error('Function not implemented.');
        },
        handleSubmit: undefined,
        description: undefined,
        inputField: undefined,
        button: undefined,
      };
    }
  }

  getCreatePopup() {
    const config: PopupConfiguration = {
      title: 'New Record',
      handleClose: this.handleCloseCreate,
      ableBgClose: true,
      handleSubmit: (control) => {
        console.log(control);
        if (control.valid) {
          console.log(control.value)
        }
        return null;
      },
      description: undefined,
      inputField: [
        {
          title: 'Date',
          type: 'date',
          placeholder: undefined,
          require: true,
          validator: (control) => {
            const i = new Date(control.value);
            if (i) {
              const today = Date.now();

              const noteMonth = this.noteData()?.at(this.atMonth())?.month;

              if (i.getMonth() + 1 === noteMonth?.month && i.getTime() < today)
                return null;
              else return { invalidDate: true } as ValidationErrors;
            }

            return null;
          },
          optionItems: undefined,
          default: undefined,
        },
        {
          type: 'option',
          title: 'Category',
          placeholder: undefined,
          require: false,
          optionItems: this.categories(),
          validator: undefined,
          default: undefined,
        },
        {
          type: 'number',
          title: 'Amount',
          placeholder: undefined,
          require: true,
          optionItems: undefined,
          validator: (control) => {
            const i = control.value;

            if (i < 0) {
              return { invalidValue: true } as ValidationErrors;
            }
            return null;
          },
          default: undefined,
        },
        {
          title: 'Label',
          type: 'text',
          placeholder: 'Gooooo',
          require: false,
          validator: undefined,
          optionItems: undefined,
          default: undefined,
        },
      ],
      button: undefined,
    };

    return config;
  }

  handleCloseUpdate = () => {
    this.updatePopup.set(false);
  };

  handleOpenUpdate = (day: Day) => {
    this.updatePopup.set(true);
    this.updateDay.set(day);
  };

  handleCloseCreate = () => {
    this.createPopup.set(false);
  };

  handleOpenCreate = () => {
    this.createPopup.set(true);
  };

  handleDelete = () => {
    console.log('Deleted!!!!!');
  };

  hadleRightBracket = () => {
    const i = this.atMonth()
    if (i + 1 >= (this.noteData()?.length ?? 0)) {
      this.atMonth.set(0);
    } else {
      this.atMonth.set(i + 1);
    }
  }

  handleLeftBracket = () => {
    const i = this.atMonth();
    if (i - 1 < 0) {
      const size = this.noteData()?.length ?? 0
      this.atMonth.set(size > 0 ? size - 1 : 0);
    } else {
      this.atMonth.set(i - 1);
    }
  }

}
