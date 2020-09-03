package org.alfredo.tekmirapedia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.helper.EnsiklopediaHelper;
import org.alfredo.tekmirapedia.model.Ensiklopedia;

public class DetailEnsiklopediaActivity extends AppCompatActivity {

    public static final String DATA_ENSIKLOPEDIA = "data_ensiklopedia";
    private TextView indo, inggris, desc;
    private Button copy;
    boolean isFavorite = false;
    private Menu menu;
    private Ensiklopedia ensiklopedia;
    private EnsiklopediaHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ensiklopedia);
        indo = findViewById(R.id.tv_detail_indo_ensik);
        inggris = findViewById(R.id.tv_detail_inggris_ensik);
        desc = findViewById(R.id.tv_detail_ensik);
        copy = findViewById(R.id.btn_copy_ensiklopedia);

        helper = EnsiklopediaHelper.getInstance(getApplicationContext());

        ensiklopedia = getIntent().getParcelableExtra(DATA_ENSIKLOPEDIA);
        setTitle(ensiklopedia.getIstilahIndo());
        indo.setText(ensiklopedia.getIstilahIndo());
        inggris.setText(ensiklopedia.getIstilahInggris());
        desc.setText(ensiklopedia.getUraian());

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Penjelasan", desc.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailEnsiklopediaActivity.this, "Berhasil Menyalin Penjelasan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        this.menu = menu;
        setFavorite();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            if (ensiklopedia != null) {
                helper.open();
                if (isFavorite) {
                    isFavorite = false;
                    helper.deleteEnsiklopedia(ensiklopedia.getIdEnsiklopedia());
                    setFavorite();
                } else {
                    isFavorite = true;
                    helper.addFavoriteEnsiklopedia(ensiklopedia);
                    setFavorite();
                }
                helper.close();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        MenuItem favorite = menu.findItem(R.id.action_fav);
        if (isFavorite) {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ensiklopedia != null) {
            isFavorite = helper.isFavoriteEnsiklopedia(ensiklopedia.getIdEnsiklopedia());
        }
        Log.d("AlreadyFavorite", String.valueOf(isFavorite));
    }
}
