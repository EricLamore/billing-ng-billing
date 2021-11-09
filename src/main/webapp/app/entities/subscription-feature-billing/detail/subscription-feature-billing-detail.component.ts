import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubscriptionFeatureBilling } from '../subscription-feature-billing.model';

@Component({
  selector: 'jhi-subscription-feature-billing-detail',
  templateUrl: './subscription-feature-billing-detail.component.html',
})
export class SubscriptionFeatureBillingDetailComponent implements OnInit {
  subscriptionFeature: ISubscriptionFeatureBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionFeature }) => {
      this.subscriptionFeature = subscriptionFeature;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
