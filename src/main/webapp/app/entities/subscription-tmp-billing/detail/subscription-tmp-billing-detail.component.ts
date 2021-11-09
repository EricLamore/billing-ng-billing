import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubscriptionTmpBilling } from '../subscription-tmp-billing.model';

@Component({
  selector: 'jhi-subscription-tmp-billing-detail',
  templateUrl: './subscription-tmp-billing-detail.component.html',
})
export class SubscriptionTmpBillingDetailComponent implements OnInit {
  subscriptionTmp: ISubscriptionTmpBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionTmp }) => {
      this.subscriptionTmp = subscriptionTmp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
