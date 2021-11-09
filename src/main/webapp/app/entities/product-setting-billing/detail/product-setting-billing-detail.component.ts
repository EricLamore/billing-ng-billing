import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSettingBilling } from '../product-setting-billing.model';

@Component({
  selector: 'jhi-product-setting-billing-detail',
  templateUrl: './product-setting-billing-detail.component.html',
})
export class ProductSettingBillingDetailComponent implements OnInit {
  productSetting: IProductSettingBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSetting }) => {
      this.productSetting = productSetting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
