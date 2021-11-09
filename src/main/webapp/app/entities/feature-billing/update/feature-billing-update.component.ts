import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFeatureBilling, FeatureBilling } from '../feature-billing.model';
import { FeatureBillingService } from '../service/feature-billing.service';

@Component({
  selector: 'jhi-feature-billing-update',
  templateUrl: './feature-billing-update.component.html',
})
export class FeatureBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    isVisible: [],
  });

  constructor(protected featureService: FeatureBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.updateForm(feature);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feature = this.createFromForm();
    if (feature.id !== undefined) {
      this.subscribeToSaveResponse(this.featureService.update(feature));
    } else {
      this.subscribeToSaveResponse(this.featureService.create(feature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeatureBilling>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(feature: IFeatureBilling): void {
    this.editForm.patchValue({
      id: feature.id,
      name: feature.name,
      lastUpdate: feature.lastUpdate,
      description: feature.description,
      isVisible: feature.isVisible,
    });
  }

  protected createFromForm(): IFeatureBilling {
    return {
      ...new FeatureBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      isVisible: this.editForm.get(['isVisible'])!.value,
    };
  }
}
