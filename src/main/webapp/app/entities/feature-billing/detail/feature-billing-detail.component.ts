import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeatureBilling } from '../feature-billing.model';

@Component({
  selector: 'jhi-feature-billing-detail',
  templateUrl: './feature-billing-detail.component.html',
})
export class FeatureBillingDetailComponent implements OnInit {
  feature: IFeatureBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.feature = feature;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
