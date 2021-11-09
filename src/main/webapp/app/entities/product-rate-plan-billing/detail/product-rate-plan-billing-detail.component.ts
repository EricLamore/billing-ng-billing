import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductRatePlanBilling } from '../product-rate-plan-billing.model';

@Component({
  selector: 'jhi-product-rate-plan-billing-detail',
  templateUrl: './product-rate-plan-billing-detail.component.html',
})
export class ProductRatePlanBillingDetailComponent implements OnInit {
  productRatePlan: IProductRatePlanBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRatePlan }) => {
      this.productRatePlan = productRatePlan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
