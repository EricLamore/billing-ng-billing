import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefundBilling } from '../refund-billing.model';

@Component({
  selector: 'jhi-refund-billing-detail',
  templateUrl: './refund-billing-detail.component.html',
})
export class RefundBillingDetailComponent implements OnInit {
  refund: IRefundBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refund }) => {
      this.refund = refund;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
