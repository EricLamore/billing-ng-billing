import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';

@Component({
  selector: 'jhi-product-rate-plan-charge-billing-detail',
  templateUrl: './product-rate-plan-charge-billing-detail.component.html',
})
export class ProductRatePlanChargeBillingDetailComponent implements OnInit {
  productRatePlanCharge: IProductRatePlanChargeBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRatePlanCharge }) => {
      this.productRatePlanCharge = productRatePlanCharge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
