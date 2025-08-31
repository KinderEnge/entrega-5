import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'home-card',
  imports: [CommonModule],
  templateUrl: './card.html',
  styleUrl: './card.less',
})
export class CardComponent {
  @Input() icon: string = '';
  @Input() label: string = '';
  @Input() dado: string | null = null;
}
