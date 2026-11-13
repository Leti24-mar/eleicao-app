import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeChefePage } from './home-chefe.page';

describe('HomeChefePage', () => {
  let component: HomeChefePage;
  let fixture: ComponentFixture<HomeChefePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeChefePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
