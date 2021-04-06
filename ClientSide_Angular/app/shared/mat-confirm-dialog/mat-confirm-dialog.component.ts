import { Component, OnInit, Inject } from '@angular/core';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-mat-confirm-dialog',
  templateUrl: './mat-confirm-dialog.component.html',
  styleUrls: ['./mat-confirm-dialog.component.css']
})
export class MatConfirmDialogComponent implements OnInit {
  title: string;
  message: string;

  constructor(public dialogRef: MatDialogRef<MatConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      if(data){
        this.message = data.message || this.message;
        // if (data.buttonText) {
        //   this.confirmButtonText = data.buttonText.ok || this.confirmButtonText;
        //   this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
        // }
          }
         // this.dialogRef.updateSize('800vw','800vw')
        }

  ngOnInit() {
  }

  onConfirm(): void {
    // Close the dialog, return true
    this.dialogRef.close(true);
  }

  onDismiss(): void {
    // Close the dialog, return false
    this.dialogRef.close(false);
  }
}


// export class ConfirmDialogModel {

//   constructor(public title: string, public message: string) {
//   }
// }
