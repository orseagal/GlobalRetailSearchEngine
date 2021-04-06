import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotificationDialogComponent } from './notification-dialog.component';
import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class NotificationDialogService {

constructor(private modalService: NgbModal) { }

public confirm(
  title: string,
  message: string,
  btnOkText: string = 'OK',
  dialogSize: 'sm'|'lg' = 'sm'): Promise<boolean> {
  const modalRef = this.modalService.open(NotificationDialogComponent, { size: dialogSize, centered:true });
  modalRef.componentInstance.title = title;
  modalRef.componentInstance.message = message;
  modalRef.componentInstance.btnOkText = btnOkText;
  
  // centered: true
  return modalRef.result;
}
}