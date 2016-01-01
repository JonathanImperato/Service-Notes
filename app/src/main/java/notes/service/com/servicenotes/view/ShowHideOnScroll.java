package notes.service.com.servicenotes.view;


public class ShowHideOnScroll /*extends ScrollDetector implements Animation.AnimationListener */ {

   /* private final FloatingActionButton fab;
    private final ActionBar actionBar;

    public ShowHideOnScroll(FloatingActionButton fab, ActionBar actionBar) {
        super(fab.getContext());
        this.fab = fab;
        this.actionBar = actionBar;
    }

    @Override
    public void onScrollDown() {
        if (!areViewsVisible()) {
            fab.setVisibility(View.VISIBLE);
            actionBar.show();
            animateFAB(R.anim.floating_action_button_show);
        }
    }

    @Override
    public void onScrollUp() {
        if (areViewsVisible()) {
            fab.setVisibility(View.GONE);
            actionBar.hide();
            animateFAB(R.anim.floating_action_button_hide);
        }
    }

    private boolean areViewsVisible() {
        return fab.getVisibility() == View.VISIBLE && actionBar.isShowing();
    }

    private void animateFAB(int anim) {
        Animation a = AnimationUtils.loadAnimation(fab.getContext(), anim);
        a.setAnimationListener(this);
        fab.startAnimation(a);
        setIgnore(true);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // Nada
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setIgnore(false);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // Nada
    }

    */
}