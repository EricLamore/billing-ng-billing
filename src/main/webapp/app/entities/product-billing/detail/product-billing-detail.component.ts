import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductBilling } from '../product-billing.model';

@Component({
  selector: 'jhi-product-billing-detail',
  templateUrl: './product-billing-detail.component.html',
})
export class ProductBillingDetailComponent implements OnInit {
  product: IProductBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
