

            public void onItemClick(List weather) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    FragmentDetails fragmentDetails = new FragmentDetails();
                    fragmentDetails.setData(weather);
                    fragmentDetails.update(weather);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragmentDetails);
                    transaction.commit();
                }
            }



            @Override
            public void onFailure(Call<Weather_class> call, Throwable t) {
                Log.i("Oleksandr", "Failure" + t);

            }
        });


    }
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
